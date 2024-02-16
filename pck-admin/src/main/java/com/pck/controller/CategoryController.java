package com.pck.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddCategoryDto;
import com.pck.domain.entity.Category;
import com.pck.domain.vo.CategoryVo;
import com.pck.domain.vo.ExcelCategoryVo;
import com.pck.domain.vo.PageVo;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.service.CategoryService;
import com.pck.utils.BeanCopyUtils;
import com.pck.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult editCategory(@RequestBody Category category) {
        // 更新
        categoryService.updateById(category);

        return ResponseResult.okResult();
    }

    /**
     * 调用easyExcel导出分类文件
     * @param response
     */
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {

        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            // 获取需要导出的数据 并封装
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            // 将数据写入到excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常也要响应JSON
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

    }
}
