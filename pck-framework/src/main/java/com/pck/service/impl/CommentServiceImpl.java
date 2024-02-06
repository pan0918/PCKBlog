package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Comment;
import com.pck.domain.vo.CommentVo;
import com.pck.domain.vo.PageVo;
import com.pck.mapper.CommentMapper;
import com.pck.service.CommentService;
import com.pck.service.UserService;
import com.pck.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-06 16:16:23
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        queryWrapper.eq(Comment::getArticleId, articleId);
        // 根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_STATUS_NORMAL);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(page.getRecords());

        // 查询所有根评论对应的子评论
        for (CommentVo commentVo : commentVos) {
            // 查询对应子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    /**
     * 获取rootId为传入的Id的子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(list);

        return commentVoList;
    }

    /**
     * 封装详细的CommentVo函数
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        // 遍历commentVos
        for (CommentVo commentVo : commentVos) {
            // 通过createBy查询用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            // 通过toCommentUserId查询用户昵称并赋值
            // 如果toCommentUserId不为-1才进行查询, -1就表示是根评论
            if(commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return commentVos;
    }
}
