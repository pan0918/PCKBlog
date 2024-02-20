package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.User;


/**
 * 用户表(User)表数据库访问层
 *
 * @author AVANTI
 * @since 2024-02-05 11:44:08
 */
public interface UserMapper extends BaseMapper<User> {

    void deleteUser(Long id);
}
