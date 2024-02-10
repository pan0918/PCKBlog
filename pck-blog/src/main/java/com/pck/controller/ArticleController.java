package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Article;
import com.pck.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test() {
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "热门文章列表获取")
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装响应
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    // 分页查询文章
    @GetMapping("/articleList")
    @SystemLog(businessName = "分页文章获取")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    // 查询文章详细内容
    @GetMapping("/{id}")
    @SystemLog(businessName = "文章详细内容获取")
    public ResponseResult getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }
}
