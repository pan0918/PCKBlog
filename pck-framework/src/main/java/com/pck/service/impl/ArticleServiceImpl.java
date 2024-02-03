package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Article;
import com.pck.domain.vo.HotArticleVo;
import com.pck.mapper.ArticleMapper;
import com.pck.service.ArticleService;
import com.pck.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    // 查询热门文章，封装响应
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 要求文章不是草稿,即status值为0
        queryWrapper.eq(Article::getStatus, 0);
        // 按点击量从上到下排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多显示10条内容
        Page<Article> page = new Page<>(1,10 );
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        // 采用Bean拷贝的方式提取Article中需要的字段
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }
}
