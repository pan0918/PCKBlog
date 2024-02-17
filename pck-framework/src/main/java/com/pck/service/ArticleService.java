package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.ResponseResult;
import com.pck.domain.dto.AddArticleDto;
import com.pck.domain.dto.UpdateArticleDto;
import com.pck.domain.entity.Article;
import com.pck.domain.vo.ArticleUpdateVo;
import com.pck.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    PageVo pageList(Integer pageNum, Integer pageSize, String title, String summary);

    ArticleUpdateVo listArticle(Long id);

    void updateArticle(UpdateArticleDto updateArticleDto);

    void deleteArticle(Long id);
}
