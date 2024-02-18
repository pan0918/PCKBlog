package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.entity.Menu;
import com.pck.domain.vo.MenuVo;
import com.pck.mapper.MenuMapper;
import com.pck.service.MenuService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员, 返回所有的权限
        if(SecurityUtils.isAdmin()) {
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

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否是管理员
        if(SecurityUtils.isAdmin()) {
            // 如果是 返回所有符合条件的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 反之返回角色所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建MenuTree形式
        // 先找第一层级, 接着找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus, 0L);

        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的 子菜单集合（Menu）
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        return childrenList;
    }


    @Override
    public List<MenuVo> listAllMenus(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();

        // 菜单模糊匹配
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        // 菜单状态正常
        queryWrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS);
        // 按父、子Id排序
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);


        List<Menu> menus = list(queryWrapper);
        // 封装Vo
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);

        return menuVos;
    }

    @Override
    public boolean hasChildrenMenu(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Menu::getParentId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public void deleteMenu(Long id) {
        menuMapper.deleteMenu(id);
    }
}
