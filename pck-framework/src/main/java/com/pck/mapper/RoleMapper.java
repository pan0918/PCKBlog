package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author AVANTI
 * @since 2024-02-13 21:08:57
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
