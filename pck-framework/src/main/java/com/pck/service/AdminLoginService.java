package com.pck.service;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
