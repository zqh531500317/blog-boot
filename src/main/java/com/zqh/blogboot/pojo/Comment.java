package com.zqh.blogboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.zqh.blogboot.validations.DoComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 评论内容
     */
    @NotBlank(groups = {DoComment.class})
    private String content;

    /**
     * 评论的文章
     */
    @TableField("article_id")
    @NotNull(groups = {DoComment.class})
    private Integer articleId;

    /**
     * 评论用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 点赞次数
     */
    private Integer like;


    @Override
    protected Serializable pkVal() {
        return this.commentId;
    }

}
