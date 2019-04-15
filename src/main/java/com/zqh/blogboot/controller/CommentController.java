package com.zqh.blogboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqh.blogboot.pojo.Comment;
import com.zqh.blogboot.pojo.User;
import com.zqh.blogboot.query.CommentResult;
import com.zqh.blogboot.service.CommentService;
import com.zqh.blogboot.validations.DoComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zqh
 * @since 2018-12-09
 */
@RestController
@Api("评论操作api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/api/comment")
    @ApiOperation("发表评论")
    public boolean comment(HttpSession httpSession, @RequestBody @Validated(value = {DoComment.class}) Comment comment, BindingResult bindingResult) {

        if (bindingResult.getAllErrors().size() != 0) {
            return false;
        }
        User user = (User) httpSession.getAttribute("user");
        comment.setUserId(user.getUserId());

        commentService.comment(comment);

        return true;
    }

    @GetMapping("/api/comment/{id}")
    @ApiOperation("获取评论列表")
    public List<CommentResult> list(@PathVariable("id") Integer articleId) {
        if (articleId == null)
            return null;
        return commentService.commentList(articleId);
    }
}
