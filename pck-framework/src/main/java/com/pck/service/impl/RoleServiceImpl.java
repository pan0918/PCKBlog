package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.dto.ChangeRoleStatusDto;
import com.pck.domain.entity.Role;
import com.pck.domain.entity.RoleMenu;
import com.pck.domain.vo.PageVo;
import com.pck.mapper.RoleMapper;
import com.pck.service.RoleMenuService;
import com.pck.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-13 21:08:59
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleMapper roleMapper;

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

    @Override
    public PageVo listAllRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 模糊匹配
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status);
        queryWrapper.orderByAsc(Role::getRoleSort);

        // 分页显示
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        // 封装Vo
        List<Role> records = page.getRecords();
        PageVo pageVo = new PageVo();
        pageVo.setRows(records);
        pageVo.setTotal(page.getTotal());

        return pageVo;
    }

    @Override
    public void changeStatus(ChangeRoleStatusDto changeRoleStatusDto) {
        // 获取对象
        Role role = getById(changeRoleStatusDto.getRoleId());

        // 修改对象状态
        role.setStatus(changeRoleStatusDto.getStatus());
        updateById(role);
    }

    @Override
    public void addRole(Role role) {

        save(role);

        if(role.getMenuIds() != null) {
            List<RoleMenu> roleMenus = role.getMenuIds().stream()
                    .map(menuId -> new RoleMenu(role.getId(), Long.parseLong(menuId)))
                    .collect(Collectors.toList());

            roleMenuService.saveBatch(roleMenus);
        }

    }

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.updateRoleMenuById(role);

    }

    @Override
    public void deleteRole(Long id) {
        roleMapper.deleteRole(id);
    }
}
