package com.pck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.entity.UserRole;
import com.pck.mapper.UserRoleMapper;
import com.pck.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-20 15:50:30
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
