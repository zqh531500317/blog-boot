package com.zqh.blogboot.query;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResult {
    private Integer userId;
    private String nickname;
    private Integer articleId;
    private Integer like;
    private String content;
    private Integer commentId;
    private LocalDateTime ctime;
}
