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
}
