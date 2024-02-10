package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.exception.SystemException;
import com.pck.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    // 登录功能实现
    @PostMapping("login")
    @SystemLog(businessName = "用户登陆")
    public ResponseResult login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            // 用户名不存在, 抛出我们自定义的异常处理方式
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    // 退出登陆功能实现
    @PostMapping("/logout")
    @SystemLog(businessName = "退出登陆")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}
