package com.zqh.blogboot.service;

import com.zqh.blogboot.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.blogboot.query.CommentResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface CommentService extends IService<Comment> {
    void comment(Comment comment);

    List<CommentResult> commentList(Integer articleId);
}
