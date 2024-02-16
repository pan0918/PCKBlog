package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddCategoryDto;
import com.pck.domain.entity.Category;
import com.pck.domain.vo.CategoryVo;
import com.pck.domain.vo.PageVo;
import com.pck.service.CategoryService;
import com.pck.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    /**
     * 分页获取分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(pageNum, pageSize);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加分类
     * @param addCategoryDto
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto) {
        // 封装
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        // 存储数据库
        categoryService.save(category);

        return ResponseResult.okResult();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategoryById(@PathVariable Long id) {
        // 逻辑删除, 将status修改为1, 不能调用MP的删除方法
        categoryService.deleteCategoryById(id);

        return ResponseResult.okResult();
    }

    /**
     * 修改分类前获取分类内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable Long id) {
        Category category = categoryService.getById(id);

        return ResponseResult.okResult(category);
    }

    @PutMapping
    public ResponseResult editCategory(@RequestBody Category category) {
        // 更新
        categoryService.updateById(category);

        return ResponseResult.okResult();
    }
}
