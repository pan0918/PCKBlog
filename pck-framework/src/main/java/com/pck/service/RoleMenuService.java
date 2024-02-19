package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.entity.Role;
import com.pck.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-18 23:28:31
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void updateRoleMenuById(Role role);
}
