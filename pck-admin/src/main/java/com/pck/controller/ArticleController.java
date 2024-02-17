package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddArticleDto;
import com.pck.domain.dto.UpdateArticleDto;
import com.pck.domain.vo.ArticleUpdateVo;
import com.pck.domain.vo.PageVo;
import com.pck.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto) {
        return articleService.add(articleDto);
    }

    /**
     * 获取所有文章
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        PageVo pageVo = articleService.pageList(pageNum, pageSize, title, summary);
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改文章前先获取文章内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult listArticle(@PathVariable Long id) {
        ArticleUpdateVo articleUpdateVo = articleService.listArticle(id);
        return ResponseResult.okResult(articleUpdateVo);
    }

    /**
     * 修改文章
     * @param updateArticleDto
     * @return
     */
    @PutMapping
    public ResponseResult updatArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        articleService.updateArticle(updateArticleDto);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id) {
        // 逻辑删除, 修改del_flag
        articleService.deleteArticle(id);
        return ResponseResult.okResult();
    }
}
