package com.zqh.blogboot.service.impl;

import com.zqh.blogboot.pojo.Category;
import com.zqh.blogboot.mapper.CategoryMapper;
import com.zqh.blogboot.query.Combine;
import com.zqh.blogboot.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectAllAndArticleNum() {
        return categoryMapper.selectAllAndArticleNum();
    }

    @Override
    public void combine(int[] ids, String newValue) {
        List<Integer> list = new ArrayList<>();
        for (int id : ids) {
            list.add(id);
        }
        //删除分类
        categoryMapper.deleteBatchIds(list);
        //添加分类
        Category category = new Category();
        category.setCategoryName(newValue).setSort(5).setCtime(LocalDateTime.now());
        categoryMapper.insert(category);
        //修改文章信息
        int categoryId = category.getCategoryId();
        Combine combine=new Combine();
        combine.setOldValue1(ids[0]).setOldValue2(ids[1]).setNewValue(categoryId);
        categoryMapper.updateArticle(combine);
    }
}
