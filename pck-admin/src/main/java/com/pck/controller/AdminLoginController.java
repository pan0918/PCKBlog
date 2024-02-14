package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.LoginUser;
import com.pck.domain.entity.Menu;
import com.pck.domain.entity.User;
import com.pck.domain.vo.AdminUserInfoVo;
import com.pck.domain.vo.RoutersVo;
import com.pck.domain.vo.UserInfoVo;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.exception.SystemException;
import com.pck.service.AdminLoginService;
import com.pck.service.BlogLoginService;
import com.pck.service.MenuService;
import com.pck.service.RoleService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    // 登录功能实现
    @PostMapping("/user/login")
    @SystemLog(businessName = "用户登陆")
    public ResponseResult login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            // 用户名不存在, 抛出我们自定义的异常处理方式
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登陆的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 根据用户Id查询全权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        // 根据用户Id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        // 获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询Menu 结果是tree形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
