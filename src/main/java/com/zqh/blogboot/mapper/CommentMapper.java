package com.zqh.blogboot.mapper;

import com.zqh.blogboot.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqh.blogboot.query.CommentResult;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface CommentMapper extends BaseMapper<Comment> {

    void comment(Comment comment);

    List<CommentResult> commentList(Integer articleId);
}
