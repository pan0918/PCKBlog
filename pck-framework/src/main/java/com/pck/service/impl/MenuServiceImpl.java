package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.entity.Menu;
import com.pck.mapper.MenuMapper;
import com.pck.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-13 21:00:42
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员, 返回所有的权限
        if(id == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(queryWrapper);
            // 仅需要perms字段内容, stream流处理
            List<String> perms = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());

            return perms;
        }

        // 其余返回所具有的权限
        // 连表查询
        return getBaseMapper().selectPermsByUserId(id);
    }
}
