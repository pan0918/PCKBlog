package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Category;
import com.pck.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-04 14:29:15
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();
}
