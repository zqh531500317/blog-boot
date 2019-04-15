package com.zqh.blogboot.service.impl;

import com.zqh.blogboot.mapper.UserMapper;
import com.zqh.blogboot.pojo.Comment;
import com.zqh.blogboot.mapper.CommentMapper;
import com.zqh.blogboot.query.CommentResult;
import com.zqh.blogboot.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void comment(Comment comment) {
        //存储评论
        commentMapper.comment(comment);
        //修改个人信息
        userMapper.incCommentNum(comment.getUserId());
    }

    @Override
    public List<CommentResult> commentList(Integer articleId) {
        return commentMapper.commentList(articleId);
    }
}
