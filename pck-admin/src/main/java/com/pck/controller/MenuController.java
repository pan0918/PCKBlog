package com.pck.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddMenuDto;
import com.pck.domain.entity.Menu;
import com.pck.domain.vo.MenuTreeVo;
import com.pck.domain.vo.MenuVo;
import com.pck.domain.vo.RoleMenuTreeSelectVo;
import com.pck.service.MenuService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.MenuTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取所有菜单
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(String status, String menuName) {
        List<MenuVo> menuVoList = menuService.listAllMenus(status, menuName);
        return ResponseResult.okResult(menuVoList);
    }

    /**
     * 添加新菜单
     * @param addMenuDto
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody AddMenuDto addMenuDto) {
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        menuService.save(menu);

        return ResponseResult.okResult();
    }

    /**
     * 修改菜单前获取菜单内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id) {
        Menu menu = menuService.getById(id);

        return ResponseResult.okResult(menu);
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        // 选择的上级菜单不能够是自己
        if(menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500, "修改菜单'" + menu.getMenuName() + "'失败,上级菜单不能选择自己");
        }
        menuService.updateById(menu);

        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id) {
        // 若选择的菜单有子菜单, 显示无法删除
        if(menuService.hasChildrenMenu(id)) {
            return ResponseResult.errorResult(500, menuService.getById(id).getMenuName() + "存在子菜单,不允许删除");
        }

        menuService.deleteMenu(id);
        return ResponseResult.okResult();
    }

    /**
     * 新增用户前先获取新增用户时所填写的菜单树
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        List<MenuTreeVo> menuTreeVos = menuService.treeSelect();

        return ResponseResult.okResult(menuTreeVos);
    }

    /**
     * 修改角色前先获取角色内容:菜单树
     * @param id
     * @return
     */
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTree(@PathVariable Long id) {
        // RoleMenu表中未存超级管理员的信息, 默认超级管理员不能修改

        // 根据角色Id查询对应的菜单Id
        List<Long> checkedKeys = menuService.selectMenuListById(id);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, 0L);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = menuService.list(queryWrapper);
        List<MenuTreeVo> menuTree = MenuTreeUtils.getMenuTree(menus);

        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(checkedKeys, menuTree);

        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }

}
