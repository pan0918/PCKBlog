package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author AVANTI
 * @since 2024-02-13 21:00:40
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);
}
