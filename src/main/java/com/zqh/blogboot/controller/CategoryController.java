package com.zqh.blogboot.controller;


import com.zqh.blogboot.pojo.Category;
import com.zqh.blogboot.service.CategoryService;
import com.zqh.blogboot.utils.AjaxResult;
import com.zqh.blogboot.validations.AddCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Api("类别操作api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @ApiOperation(value = "获取所有分类")
    @GetMapping("/api/category/list")
    public List<Category> list() {
        return categoryService.list();
    }

    @ApiOperation(value = "获取所有分类和对应文章数,文章数为0的不返回")
    @GetMapping("/api/category/listWithCount")
    public List<Category> listWithArticleNum() {
        return categoryService.selectAllAndArticleNum();
    }

    @ApiOperation(value = "添加分类")
    @PostMapping("/admin/category/")
    public AjaxResult add(@RequestBody @Validated(value = {AddCategory.class}) Category category) {
        category.setCtime(LocalDateTime.now());
        AjaxResult ajaxResult = new AjaxResult();
        try {
            boolean b = categoryService.save(category);
            ajaxResult.setResult(b);
        } catch (Exception e) {
            ajaxResult.setResult(false);
        }
        ajaxResult.autoData();
        return ajaxResult;
    }

    @ApiOperation(value = "合并分类")
    @PutMapping("/admin/category/combine")
    public void combine(int[] ids,@NotNull String newValue) {
        logger.info("进行combine操作"+newValue);
        if (ids.length!=2){
            return;
        }
        categoryService.combine(ids,newValue);
    }
}
