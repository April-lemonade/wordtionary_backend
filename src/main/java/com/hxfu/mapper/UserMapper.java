package com.hxfu.mapper;

import com.hxfu.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User login(String openid);

    int addUser(String openid);

    int saveInfo(User user);
}
