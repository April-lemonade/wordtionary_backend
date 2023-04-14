package com.hxfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hxfu.entity.Word;
import com.hxfu.mapper.WordMapper;
import com.hxfu.service.WordService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;


    @Override
    public List<Word> getWords(String bookId, String wordId, String dictionaryId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        List<Word> words = wordMapper.getWords(Integer.parseInt(bookId), Integer.parseInt(wordId));
        for (Word word : words) {
            if (word.getOxfordTranslations() == null && Integer.parseInt(dictionaryId) == 0) {
                wordMapper.addOxfordTranslation(Oxford(word).toString(), word.getId());
            }
            if (word.getCambridgeTranslations() == null && Integer.parseInt(dictionaryId) == 1) {
                System.out.println(Cambridge(word));
                wordMapper.addCambridgeTranslation(Cambridge(word), word.getId());
            }
            /*String url = Base_URL + "/translations/en/zh/" + word.getWord();
            // 创建一个请求头对象
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置参数
            httpHeaders.set("Accept", "application/json");
            httpHeaders.set("app_id", Application_ID);
            httpHeaders.set("app_key", Application_Keys);

            // 创建一个响应体对象
            HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
            // 发送GET请求
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            // 获取响应对象里的 body 对象
//            Map<String, Object> body = responseEntity.getBody();
            JSONObject body = JSONObject.parseObject(responseEntity.getBody());
//            word.setTranslations();

            JSONObject results = (JSONObject) body.getJSONArray("results").get(0);
            results = results.getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries").getJSONObject(0);
//            results = results.getJSONArray("entries").getJSONObject(0);
            word.setTranslations(results);*/
//            String brisound = results.getJSONArray("pronunciations").getJSONObject(0).get("audioFile").toString();
//            String unisound = results.getJSONArray("pronunciations").getJSONObject(1).get("audioFile").toString();
//            results = results.getJSONArray("pronunciations").getJSONObject(0);
//            String[] files = new String[2];
//            files[0] = brisound;
//            files[1] = unisound;
//            word.setAudioFile(files);


        }
        return wordMapper.getWords(Integer.parseInt(bookId), Integer.parseInt(wordId));
    }

    @Override
    public List<Word> showWords(String bookId, String wordId) {
        return wordMapper.showWords(Integer.parseInt(bookId), Integer.parseInt(wordId));
    }

    public JSONObject Oxford(Word word) {
        String Base_URL = "https://od-api.oxforddictionaries.com/api/v2";
        String Application_ID = "55a7fbae";
        String Application_Keys = "9dfe609ca8f85418c7e3bc0af8a00889";
        RestTemplate restTemplate = new RestTemplate();

        String url = Base_URL + "/translations/en/zh/" + word.getWord();
        // 创建一个请求头对象
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置参数
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("app_id", Application_ID);
        httpHeaders.set("app_key", Application_Keys);

        // 创建一个响应体对象
        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
        // 发送GET请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        // 获取响应对象里的 body 对象
//            Map<String, Object> body = responseEntity.getBody();
        JSONObject body = JSONObject.parseObject(responseEntity.getBody());
//            word.setTranslations();

        JSONObject results = (JSONObject) body.getJSONArray("results").get(0);
        results = results.getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("entries").getJSONObject(0);
        return results;
    }

    public String Cambridge(Word word) throws IOException {
        String url = "https://dictionary.cambridge.org/zhs/%E8%AF%8D%E5%85%B8/%E8%8B%B1%E8%AF%AD-%E6%B1%89%E8%AF%AD-%E7%AE%80%E4%BD%93/" + word.getWord();
        System.out.println(url);
        Document document = Jsoup.parse(new URL(url), 10000);
        /*Document document = null;
        Connection con = Jsoup.connect(url).ignoreHttpErrors(true).userAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .timeout(30000); // 设置连接超时时间
        Connection.Response response = con.execute();
        if (response.statusCode() == 200) {
            document = con.get();
        } else {
            System.out.println(response.statusCode());
            return null;
        }*/
        Elements definitions = document.getElementsByClass("def ddef_d db");
        Elements deftrans = document.getElementsByClass("trans dtrans dtrans-se  break-cj");
        Elements examples = document.getElementsByClass("def-body ddef_b");
        ArrayList<String> def = iteratorget(definitions.listIterator());
        ArrayList<String> deftranslation = iteratorget(deftrans.listIterator());
//        ArrayList<String> examp = iteratorget(examples.listIterator());
        ArrayList<String> examp = deal(deftrans, examples);
        ArrayList<JSONObject> arr = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < def.size(); i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("definition",def.get(i));
            jsonObject1.put("examples",examp.get(i));
            arr.add(jsonObject1);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject.put("definition", def);
//        jsonObject.put("deftrans", deftranslation);
        jsonObject.put("examples", examp);
//        return jsonObject.toString();
        return arr.toString();
    }

    public ArrayList<String> iteratorget(Iterator iterator) {
        ArrayList<String> arrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            Element j = (Element) iterator.next();
            arrayList.add(j.text());
        }
        return arrayList;
    }

    public ArrayList<String> deal(Elements deftrans, Elements examples) {
        ArrayList<String> res = new ArrayList<>();
//        Elements elements = new Elements();
        Iterator defiter = deftrans.listIterator();
        Iterator examiter = examples.listIterator();
        while (defiter.hasNext() && examiter.hasNext()) {
            Element deftran = (Element) defiter.next();
            Element example = (Element) examiter.next();
            String temp = example.html();
            temp.replace(deftran.html(), "");
            res.add(temp);
            /*String tmpString = deftran.html();
            StringBuffer stringBuffer = new StringBuffer(example.html());
            System.out.println(tmpString);
            System.out.println(stringBuffer);
            int iFlag = -1;
            do {
                iFlag = stringBuffer.indexOf(tmpString);
                if (iFlag != -1) {
                    stringBuffer.deleteCharAt(iFlag);
                }
                res.add(new String(stringBuffer));
            } while (iFlag != -1);*/
//                Element element = new Element(stringBuffer);

        }
        return res;
    }
}
