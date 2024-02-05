package com.pck.service;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
