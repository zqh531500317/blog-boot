package com.zqh.blogboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.zqh.blogboot.validations.AddUser;
import com.zqh.blogboot.validations.ApiAddUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

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
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    @Length(max = 50, min = 3, groups = {AddUser.class, ApiAddUser.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(groups = {AddUser.class, ApiAddUser.class})
    private String password;

    /**
     * 昵称
     */
    @Length(max = 50, min = 1, groups = {AddUser.class, ApiAddUser.class})
    private String nickname;

    /**
     * 邮箱
     */
    @Email(groups = {AddUser.class, ApiAddUser.class})
    private String email;

    /**
     * 账号是否锁定
     */

    private Boolean locked;

    /**
     * 文章数
     */
    @TableField("article_num")
    private Integer articleNum;

    /**
     * 评论数
     */
    @TableField("comment_num")
    private Integer commentNum;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 角色(1:管理员,2:普通角色)
     */
    @Range(min = 1, max = 2, groups = {AddUser.class})
    @Null(groups = {ApiAddUser.class})
    private Integer role;

    /**
     * 逻辑删除 1：已删除 0：正常
     */
    @TableLogic
    @TableField("is_delete")
    private Boolean isDelete;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
