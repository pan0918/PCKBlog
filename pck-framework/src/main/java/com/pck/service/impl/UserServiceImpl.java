package com.pck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.entity.User;
import com.pck.mapper.UserMapper;
import com.pck.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-06 22:41:42
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
