package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Comment;
import com.pck.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 获取评论
    @GetMapping("/commentList")
    @SystemLog(businessName = "评论信息获取")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    // 文章评论
    @PostMapping
    @SystemLog(businessName = "文章评论获取")
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    // 友链评论
    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "友情链接评论获取")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum, pageSize);
    }
}
