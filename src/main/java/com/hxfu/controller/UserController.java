package com.hxfu.controller;

import com.hxfu.entity.User;
import com.hxfu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public User listusers(@RequestBody String code) {
        return userService.login(code);
    }

    @PostMapping("/saveinfo")
    public int saveInfo( String avatarUrl, String name, String openid) {
//        avatarUrl= avatarUrl.substring(11);
        System.out.println(avatarUrl);
        System.out.println(name);
        System.out.println(openid);
        User user = new User();
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setId(openid);
        return userService.saveInfo(user);
    }
}
