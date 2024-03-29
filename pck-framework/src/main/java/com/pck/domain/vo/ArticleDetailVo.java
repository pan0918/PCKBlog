package com.pck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文章具体内容vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    // 标题
    private String title;
    // 文章摘要
    private String summary;
    // 文章所属分类id
    private Long categoryId;
    // 文章所属分类名
    private String categoryName;
    // 缩略图
    private String thumbnail;
    // 文章内容
    private String content;
    // 访问量
    private Long viewCount;
    // 创建时间
    private Date createTime;
}
