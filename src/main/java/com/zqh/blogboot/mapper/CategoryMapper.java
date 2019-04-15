package com.zqh.blogboot.mapper;

import com.zqh.blogboot.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqh.blogboot.query.Combine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> selectAllAndArticleNum();

    void updateArticle(Combine combine);
}
