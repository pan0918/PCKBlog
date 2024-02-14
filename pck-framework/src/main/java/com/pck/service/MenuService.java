package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-13 21:00:42
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}