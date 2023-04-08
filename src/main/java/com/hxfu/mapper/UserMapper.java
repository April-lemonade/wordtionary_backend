package com.hxfu.mapper;

import com.hxfu.entity.User;

public interface UserMapper {
    User login(String openid);
//    int findUser(String openid);
    int addUser(String openid);
}
