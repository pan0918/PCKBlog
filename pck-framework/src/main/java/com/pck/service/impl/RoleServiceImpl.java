package com.pck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.entity.Role;
import com.pck.mapper.RoleMapper;
import com.pck.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-13 21:08:59
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 是仅需返回admin
        if(id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("Admin");

            return roleKeys;
        }
        // 否则查询用户所具有的角色信息


        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}
