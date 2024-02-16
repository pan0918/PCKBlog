package com.pck.service.impl;

import com.pck.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        // 如果是超级管理员，直接返回TRUE
        if(SecurityUtils.isAdmin()) {
            return true;
        }
        // 否则获取当前登陆用户所具有的权限
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
