package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;
import com.pck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 获取个人中心信息
    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    // 更新个人信息
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册新用户")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
