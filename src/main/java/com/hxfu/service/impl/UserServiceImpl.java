package com.hxfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hxfu.entity.User;
import com.hxfu.mapper.UserMapper;
import com.hxfu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String code) {
        code = code.substring(5);
        System.out.println(code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxde8ccf1ae1561d4e&secret=80f3202bf55fa59663fc611b5d0b98dc&js_code=" + code + "&grant_type=authorization_code";
        // 创建一个请求头对象
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        // 设置参数
        httpHeaders.set("Accept", "application/json");

        // 创建一个响应体对象
        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
        // 发送GET请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(responseEntity.toString());
        // 获取响应对象里的 body 对象
        JSONObject data = JSONObject.parseObject(responseEntity.getBody());
        String openid = data.getString("openid");
        if (userMapper.login(openid) == null) {
            userMapper.addUser(openid);
        }
        return userMapper.login(openid);
    }

    @Override
    public int saveInfo(User user) {
        return userMapper.saveInfo(user);
    }

    @Override
    public int updatewordid(String openid, int wordId) {
        return userMapper.updatewordid(openid, wordId);
    }
}
