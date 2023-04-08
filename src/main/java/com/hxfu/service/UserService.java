package com.hxfu.service;

import com.hxfu.entity.User;

public interface UserService {
    User login(String code);
    int saveInfo(User user);
}
