package com.hxfu.mapper;

import com.hxfu.entity.User;

public interface UserMapper {
    User login(String openid);

    int addUser(String openid);

    int saveInfo(User user);

    int updatewordid(String openid, int wordId);
}
