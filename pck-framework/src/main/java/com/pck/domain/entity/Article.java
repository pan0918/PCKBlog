package com.pck.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章表(Article)表实体类
 *
 * @author AVANTI
 * @since 2024-02-03 15:42:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pck_article") // 与数据库表名映射
public class Article {
    @TableId
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;


    /**
     * 分类表(Category)表实体类
     *
     * @author AVANTI
     * @since 2024-02-04 14:29:14
     */
    @SuppressWarnings("serial")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @TableName("pck_category")
    public static class Category {
        @TableId
        private Long id;

        //分类名
        private String name;
        //父分类id，如果没有父分类为-1
        private Long pid;
        //描述
        private String description;
        //状态0:正常,1禁用
        private String status;

        private Long createBy;

        private Date createTime;

        private Long updateBy;

        private Date updateTime;
        //删除标志（0代表未删除，1代表已删除）
        private Integer delFlag;


    }
}

