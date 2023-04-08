package com.hxfu.service.impl;

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
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;

    private String Base_URL = "https://od-api.oxforddictionaries.com/api/v2";
    private String Application_ID = "55a7fbae";
    private String Application_Keys = "9dfe609ca8f85418c7e3bc0af8a00889";

    @Override
    public List<Word> getWords(int count) {
        RestTemplate restTemplate = new RestTemplate();

        List<Word> words = wordMapper.getWords(count);
        for (Word word : words) {
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
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class);

            // 获取响应对象里的 body 对象
            Map<String, Object> body = responseEntity.getBody();
//            word.setTranslations();
            System.out.println(body.toString());


        }
        return wordMapper.getWords(count);
    }
}
