package com.pck.utils;

import com.pck.domain.entity.Menu;
import com.pck.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取菜单树方法
 */
public class MenuTreeUtils {

    /**
     * 获取菜单树
     * @param menus
     * @return
     */
    public static List<MenuTreeVo> getMenuTree(List<Menu> menus) {
        List<MenuTreeVo> menuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());

        List<MenuTreeVo> menuTreeVoList = menuTreeVos.stream()
                .filter(m -> m.getParentId().equals(0L))
                .map(c -> c.setChildren(getChildrenList(menuTreeVos, c)))
                .collect(Collectors.toList());

        return menuTreeVoList;
    }

    /**
     * 获取以root为根的子节点列表
     * @param list
     * @param root
     * @return
     */
    public static List<MenuTreeVo> getChildrenList(List<MenuTreeVo> list, MenuTreeVo root) {
        // 获取以root为根的菜单树
        List<MenuTreeVo> menuTreeVos = list.stream()
                .filter(m -> root.getId().equals(m.getParentId()))
                .map(c -> c.setChildren(getChildrenList(list, c)))
                .collect(Collectors.toList());


        return menuTreeVos;
    }
}
