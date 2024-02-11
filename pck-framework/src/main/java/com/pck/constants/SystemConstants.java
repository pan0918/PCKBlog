package com.pck.constants;

public class SystemConstants {
    /**
     * 字面量处理,增强代码的的维护性
     */

    // 文章是草稿状态
    public static final int ARTICLE_STATUS_DRAFT = 1;
    // 文章是正常状态
    public static final int ARTICLE_STATUS_NORMAL = 0;
    // 文章列表当前查询页数
    public static final int ARTICLE_STATUS_CURRENT = 1;
    // 文章列表每页显示的数据条数
    public static final int ARTICLE_STATUS_SIZE = 10;
    // 分类为正常状态
    public static final String STATUS_NORMAL = "0";

    // 友情链接审核通过状态
    public static final String LINK_STATUS_NORMAL = "0";

    // 根评论状态
    public static final int ROOT_STATUS_NORMAL = -1;

    // 评论类型
    public static final String ARTICLE_COMMENT = "0";
    public static final String LINK_COMMENT = "1";

    // 文章浏览量常量
    public static final String ARTICLE_VIEW_COUNT = "articleViewCount";

}
