package com.hxfu.service;

import com.hxfu.entity.User;

public interface UserService {
    User login(String code);
    int saveInfo(User user);
    int updatewordid(String openid, int wordId);
    int setAccount(String openid, String account, String pwd);
}
