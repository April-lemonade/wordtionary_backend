package com.hxfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hxfu.entity.Record;
import com.hxfu.entity.Word;
import com.hxfu.mapper.RecordMapper;
import com.hxfu.mapper.UserMapper;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Word> getWords(String bookId, String wordId, String dictionaryId, String openid) throws IOException {
        List<Word> allWords = new ArrayList<>();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        //取复习的单词
        List<Integer> reviewIds = recordMapper.getReview(openid, time);
        List<Word> reviewWords = new ArrayList<>();
        for (int i : reviewIds) {
            Word reviewWord = wordMapper.getReviewWords(i);
            List<Record> records = recordMapper.getFamiliarCount(openid, i);
            Record lastRecord = records.get(records.size() - 1);
            if (!format.format(lastRecord.getDate()).equals(time)) { //今天还没学过
                allWords.add(reviewWord);
                reviewWords.add(reviewWord);
            }
        }
        //取新学的单词
        int dailyCount = userMapper.getDailyCount(openid);
        int learnedCount = 0;
        List<Record> todayRecords = recordMapper.getTodayRecord(openid, time);
        for (Record todayRecord : todayRecords) {
            List<Record> records = recordMapper.getFamiliarCount(openid, Integer.parseInt(todayRecord.getWordId()));
            if (format.format(records.get(0).getDate()).equals(time)) {
                learnedCount = learnedCount + 1;
            }
        }
        System.out.println("|||||||||||||" + learnedCount);
        if (learnedCount < dailyCount) {
            List<Word> words = wordMapper.getWords(Integer.parseInt(bookId), Integer.parseInt(wordId), dailyCount - learnedCount);
            getMeanning(dictionaryId, words);
            List<Word> newWords = wordMapper.getWords(Integer.parseInt(bookId), Integer.parseInt(wordId), dailyCount - learnedCount);
            allWords.addAll(newWords);
        }

        return allWords;
    }

    @Override
    public List<Word> getOneWords(int bookId, int wordId, String dictionaryId, int dailyCount) throws IOException {

        List<Word> words = wordMapper.getWords(bookId, wordId, dailyCount);
        getMeanning(dictionaryId, words);
        return wordMapper.getWords(bookId, wordId, dailyCount);
    }

    public void getMeanning(String dictionaryId, List<Word> words) throws IOException {
        for (Word word : words) {
            if (word.getOxfordTranslations() == null && Integer.parseInt(dictionaryId) == 0) {
                wordMapper.addOxfordTranslation(Oxford(word).toString(), word.getId());
            }
            if (word.getCambridgeTranslations() == null && Integer.parseInt(dictionaryId) == 1) {
                System.out.println(Cambridge(word));
                wordMapper.addCambridgeTranslation(Cambridge(word), word.getId());
            }
        }
    }

    @Override
    public List<Word> showWords(String bookId, String wordId) {
        return wordMapper.showWords(Integer.parseInt(bookId), Integer.parseInt(wordId));
    }

    @Override
    public List<Word> getRelearn(String openid) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        List<Integer> relearnIds = recordMapper.getRelearn(time, openid);
        List<Word> relearnWords = new ArrayList<>();
        for (int i : relearnIds) {
            Word relearnWord = wordMapper.getReviewWords(i);
            List<Record> records = recordMapper.getFamiliarCount(openid, relearnWord.getId());
            System.out.println("可能需要重学的单词的最后一次记忆" + records.get(records.size() - 1));

            if (records.get(records.size() - 1).getFamiliar() == -1) {
                //reviewWords.add(relearnWord);
                //allWords.add(relearnWord);
                relearnWords.add(relearnWord);
                System.out.println("要重新学的单词！" + relearnWord.toString());
            }
        }
        int currentId = userMapper.getWordId(openid);
        int listid = userMapper.getListId(openid);
        int allLastId = wordMapper.getLastId(listid);
        int allFamiliarCount = userMapper.getFamiliar(openid).split(",").length;
        int allRelearn = recordMapper.getAllRelearn(openid,listid,allFamiliarCount);
        if (currentId == allLastId && allRelearn == 0) {
            Word flag = new Word();
            flag.setId(-1);
            relearnWords.add(flag);
        }
        return relearnWords;
    }

    @Override
    public Word searchWord(String word, String dictionaryId) throws IOException {
        Word search = wordMapper.searchWord(word).get(0);
        if (search.getOxfordTranslations() == null && Integer.parseInt(dictionaryId) == 0) {
            wordMapper.addOxfordTranslation(Oxford(search).toString(), search.getId());
        }
        if (search.getCambridgeTranslations() == null && Integer.parseInt(dictionaryId) == 1) {
            System.out.println(Cambridge(search));
            wordMapper.addCambridgeTranslation(Cambridge(search), search.getId());
        }
        return wordMapper.searchWord(word).get(0);
    }

    @Override
    public int addWord(String name, int listId) {
        Word word = new Word();
        word.setWord(name);
        word.setListid(listId);
        return wordMapper.addWord(word);
    }

    @Override
    public int[] getPredict(String openid) {
        int[] predict = new int[7];
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, i);
            Date newDate = calendar.getTime();
            List<Integer> reviewIds = recordMapper.getReview(openid, format.format(newDate));
            predict[i] = reviewIds.size();
        }
        return predict;
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
            jsonObject1.put("definition", def.get(i));
            jsonObject1.put("examples", examp.get(i));
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
        Iterator defiter = deftrans.listIterator();
        Iterator examiter = examples.listIterator();
        while (defiter.hasNext() && examiter.hasNext()) {
            Element deftran = (Element) defiter.next();
            Element example = (Element) examiter.next();
            String temp = example.html();
            temp.replace(deftran.html(), "");
            res.add(temp);

        }
        return res;
    }
}
