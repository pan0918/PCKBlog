package com.pck.service.impl;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.LoginUser;
import com.pck.domain.entity.User;
import com.pck.domain.vo.BlogUserLoginVo;
import com.pck.domain.vo.UserInfoVo;
import com.pck.service.BlogLoginService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.JwtUtil;
import com.pck.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("bloglogin"+userId,loginUser);
        // 把token和userInfo封装 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo );
    }

    @Override
    public ResponseResult logout() {
        // 获取token 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userId
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin"+userId);
        return ResponseResult.okResult();
    }
}
