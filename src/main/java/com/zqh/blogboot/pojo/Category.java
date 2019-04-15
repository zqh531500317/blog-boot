package com.zqh.blogboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.zqh.blogboot.validations.AddCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;
    @NotBlank(groups = {AddCategory.class})
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 创建时间
     */
    @Null(groups = {AddCategory.class})
    private LocalDateTime ctime;

    /**
     * 排序，越大越优先
     */
    @Min(value = 0, groups = {AddCategory.class})
    @Max(value = 10, groups = {AddCategory.class})
    private Integer sort;

    /**
     * 该分类下的文章数
     */
    @TableField(exist = false)
    private Integer articleNum;

    @Override
    protected Serializable pkVal() {
        return this.categoryId;
    }

}
