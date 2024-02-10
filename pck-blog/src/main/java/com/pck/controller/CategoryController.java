package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 查询所有分类列表
    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "分类标签获取")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }

}
