package com.pck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.domain.entity.ArticleTag;
import com.pck.mapper.ArticleTagMapper;
import com.pck.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-15 22:46:29
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
