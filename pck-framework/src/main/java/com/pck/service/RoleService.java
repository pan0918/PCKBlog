package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.dto.ChangeRoleStatusDto;
import com.pck.domain.entity.Role;
import com.pck.domain.vo.PageVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-13 21:08:58
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    PageVo listAllRole(Integer pageNum, Integer pageSize, String roleName, String status);

    void changeStatus(ChangeRoleStatusDto changeRoleStatusDto);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);
}
