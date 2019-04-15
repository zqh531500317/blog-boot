package com.zqh.blogboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.zqh.blogboot.validations.AddArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

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
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 分类
     */
    @TableField(value = "category_id")
    @NotNull(groups = {AddArticle.class})
    private Integer categoryId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = {AddArticle.class})
    private String title;

    /**
     * 概要
     */
    @NotBlank(message = "概要不能为空", groups = {AddArticle.class})
    private String desc;

    /**
     * 正文
     */
    @NotBlank(message = "正文不能为空", groups = {AddArticle.class})
    private String content;

    /**
     * 点赞数
     */
    @Max(value = 0, groups = {AddArticle.class})
    @Min(value = 0, groups = {AddArticle.class})
    private Integer like;

    /**
     * 浏览数
     */
    @Max(value = 0, groups = {AddArticle.class})
    @Min(value = 0, groups = {AddArticle.class})
    private Integer view;

    /**
     * 是否为推荐,1为推荐
     */
    @NotNull(groups = {AddArticle.class})
    @TableField(value = "is_top")
    private Boolean isTop;

    /**
     * 创建时间
     */
    @Null(groups = {AddArticle.class})
    private Date ctime;

    /**
     * 更新时间
     */
    @Null(groups = {AddArticle.class})
    private Date utime;


    @Override
    protected Serializable pkVal() {
        return this.articleId;
    }
}