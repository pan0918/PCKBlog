package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;
import com.pck.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    // 登录功能实现
    @PostMapping("login")
    public ResponseResult login(@RequestBody User user) {
        return blogLoginService.login(user);
    }
}
