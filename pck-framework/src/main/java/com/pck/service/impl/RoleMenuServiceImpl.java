package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.entity.Role;
import com.pck.domain.entity.RoleMenu;
import com.pck.mapper.RoleMenuMapper;
import com.pck.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-18 23:28:32
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void updateRoleMenuById(Role role) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, role.getId());

        remove(queryWrapper);

        List<String> menuIds = role.getMenuIds();

        List<RoleMenu> roleMenus = menuIds.stream()
                .map(id -> new RoleMenu(role.getId(), Long.parseLong(id)))
                .collect(Collectors.toList());

        saveBatch(roleMenus);
    }
}
