package com.zqh.blogboot.service.impl;

import com.zqh.blogboot.pojo.Category;
import com.zqh.blogboot.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void combine() {
        int count1 = categoryService.count();
        Category c1 = new Category();
        Category c2 = new Category();
        c1.setCtime(LocalDateTime.now()).setSort(5).setCategoryName(UUID.randomUUID().toString());
        c2.setCtime(LocalDateTime.now()).setSort(5).setCategoryName(UUID.randomUUID().toString());
        categoryService.save(c1);
        categoryService.save(c2);
        int count2 = categoryService.count();
        assertEquals(count1 + 2, count2);
        categoryService.combine(new int[]{c1.getCategoryId(), c2.getCategoryId()}, UUID.randomUUID().toString());
        int count3 = categoryService.count();
        assertEquals(count1 + 1, count2);

    }
}