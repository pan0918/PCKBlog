package com.pck.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Role;
import com.pck.domain.entity.User;
import com.pck.domain.vo.PageVo;
import com.pck.domain.vo.UserRoleVo;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.exception.SystemException;
import com.pck.service.RoleService;
import com.pck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 分页获取所有用户信息
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phoneNumber
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listALLUser(Integer pageNum, Integer pageSize, String userName, String phoneNumber, String status) {
        PageVo pageVo = userService.listAllUser(pageNum, pageSize, userName, phoneNumber, status);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody User user) {
        // 对传入的内容进行校验
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        userService.addUser(user);

        return ResponseResult.okResult();
    }

    /**
     * 修改用户前先回显用户内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable Long id) {
        UserRoleVo userRoleVo = userService.getUserById(id);

        return ResponseResult.okResult(userRoleVo);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public ResponseResult updateUser(@RequestBody User user) {
        userService.updateUser(user);

        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id) {
        // 逻辑删除用户
        userService.deleteUser(id);

        return ResponseResult.okResult();
    }
}
