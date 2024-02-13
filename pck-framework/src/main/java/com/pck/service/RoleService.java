package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-13 21:08:58
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
