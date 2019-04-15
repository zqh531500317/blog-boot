package com.zqh.blogboot.service;

import com.zqh.blogboot.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface CategoryService extends IService<Category> {
    List<Category> selectAllAndArticleNum();

    void combine(int[] ids, String newValue);
}
