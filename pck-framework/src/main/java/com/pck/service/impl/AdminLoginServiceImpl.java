package com.pck.service.impl;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.LoginUser;
import com.pck.domain.entity.User;
import com.pck.domain.vo.BlogUserLoginVo;
import com.pck.domain.vo.UserInfoVo;
import com.pck.service.AdminLoginService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.JwtUtil;
import com.pck.utils.RedisCache;
import com.pck.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 判断认证是否通过
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户或密码错误");
        }

        // 获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("AdminLogin"+userId,loginUser);
        // 把token封装 返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取当前登陆用户Id
        Long userId = SecurityUtils.getUserId();
        // 删除redis中对应的值
        redisCache.deleteObject("AdminLogin"+userId);
        return ResponseResult.okResult();
    }

}
