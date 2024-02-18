package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddMenuDto;
import com.pck.domain.entity.Menu;
import com.pck.domain.vo.MenuVo;
import com.pck.service.MenuService;
import com.pck.utils.BeanCopyUtils;
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
}
