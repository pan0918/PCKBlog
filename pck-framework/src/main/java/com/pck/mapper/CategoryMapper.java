package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.Category;

/**
 * 分类表数据库访问层
 * @author AVANTI
 * @since 2024-2-4 14:36:20
 */

public interface CategoryMapper extends BaseMapper<Category> {
    void deleteCategoryById(Long id);
}
