package com.hxfu.mapper;

import com.hxfu.entity.User;

public interface UserMapper {
    User login(String openid);

    int addUser(String openid);

    int saveInfo(User user);

    int updatewordid(String openid, int wordId);

    int changeDictionary(String openid, int dictionaryId);

    int setAccount(String openid, String account, String pwd);

    User webLogin(String account, String pwd);

    int getWordId(String openid);

    String getFamiliar(String openid);

    int changeFamiliar(String openid, String familiar);
}
