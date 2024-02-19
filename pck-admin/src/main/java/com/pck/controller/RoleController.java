package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.dto.ChangeRoleStatusDto;
import com.pck.domain.entity.Role;
import com.pck.domain.vo.PageVo;
import com.pck.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 显示所有角色
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listAllRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 返回一个PageVo对象
        PageVo pageVo = roleService.listAllRole(pageNum, pageSize, roleName, status);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改角色状态
     * @param changeRoleStatusDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        roleService.changeStatus(changeRoleStatusDto);

        return ResponseResult.okResult();
    }

    /**
     * 添加新用户
     * @param role
     * @return
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role) {
        roleService.addRole(role);

        return ResponseResult.okResult();
    }

    /**
     * 修改角色前先获取角色内容（不包括菜单树）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult seekRole(@PathVariable Long id) {
        Role role = roleService.getById(id);

        return ResponseResult.okResult(role);
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        // 逻辑删除用户
        roleService.deleteRole(id);
        return ResponseResult.okResult();
    }
}
