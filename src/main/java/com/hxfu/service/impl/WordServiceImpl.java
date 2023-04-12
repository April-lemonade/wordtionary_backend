package com.hxfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hxfu.entity.Word;
import com.hxfu.mapper.WordMapper;
import com.hxfu.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;


    @Override
    public List<Word> getWords(String bookId, String wordId) {
        RestTemplate restTemplate = new RestTemplate();

        List<Word> words = wordMapper.getWords(Integer.parseInt(bookId), Integer.parseInt(wordId));
        for (Word word : words) {
            if (word.getOxfordTranslations() == null) {
                wordMapper.addOxfordTranslation(Oxford(word).toString(), word.getId());
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
        return wordMapper.showWords(Integer.parseInt(bookId),Integer.parseInt(wordId));
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
}
