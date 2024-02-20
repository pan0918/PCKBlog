package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Role;
import com.pck.domain.entity.User;
import com.pck.domain.entity.UserRole;
import com.pck.domain.vo.PageVo;
import com.pck.domain.vo.UserInfoVo;
import com.pck.domain.vo.UserRoleVo;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.exception.SystemException;
import com.pck.mapper.UserMapper;
import com.pck.service.RoleService;
import com.pck.service.UserRoleService;
import com.pck.service.UserService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-06 22:41:42
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户Id查询用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断 null ""
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行是否重复判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        // 对密码进行加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        // 存入数据库
        save(user);

        return ResponseResult.okResult();
    }

    @Override
    public PageVo listAllUser(Integer pageNum, Integer pageSize, String userName, String phoneNumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 模糊匹配状态用户名
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        // 精确匹配手机号和状态
        queryWrapper.eq(StringUtils.hasText(phoneNumber), User::getPhonenumber, phoneNumber);
        queryWrapper.eq(StringUtils.hasText(status), User::getStatus, status);

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo =  new PageVo();
        pageVo.setRows(page.getRecords());
        pageVo.setTotal(page.getTotal());

        return pageVo;
    }

    @Override
    public void addUser(User user) {
        // 对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 新增用户
        save(user);
        // 新增用户与角色关联
        List<UserRole> userRoles = user.getRoleIds().stream()
                .map(id -> new UserRole(user.getId(), Long.parseLong(id)))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUserName, userName);

        return count(queryWrapper) > 0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber, user.getPhonenumber());

        return count(queryWrapper) > 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.getEmail());

        return count(queryWrapper) > 0;
    }

    @Override
    public UserRoleVo getUserById(Long id) {
        User user = getById(id);


        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<String> roleIds = userRoles.stream()
                .map(userRole -> userRole.getRoleId().toString())
                .collect(Collectors.toList());
//        List<Role> roles = roleIds.stream()
//                .map(rid -> roleService.getById(rid))
//                .collect(Collectors.toList());

        // 获取所有角色
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getStatus, SystemConstants.ROLE_STATUS);
        List<Role> roleList = roleService.list(roleLambdaQueryWrapper);

        return new UserRoleVo(roleIds, roleList, user);
    }

    @Override
    public void updateUser(User user) {
        // 更新用户
        updateById(user);
        // 更新UserRole表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(queryWrapper);

        List<UserRole> userRoles = user.getRoleIds().stream()
                .map(id -> new UserRole(user.getId(), Long.parseLong(id)))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);

    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);

        return count(queryWrapper) > 0;
    }
}
