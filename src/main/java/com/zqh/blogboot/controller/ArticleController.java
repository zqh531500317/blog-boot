package com.zqh.blogboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqh.blogboot.pojo.Article;
import com.zqh.blogboot.pojo.User;
import com.zqh.blogboot.query.ArticleQuery;
import com.zqh.blogboot.query.ArticleResult;
import com.zqh.blogboot.service.ArticleService;
import com.zqh.blogboot.service.CategoryService;
import com.zqh.blogboot.utils.AjaxResult;
import com.zqh.blogboot.validations.AddArticle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@Api("文章操作api")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取所有文章,可筛选")
    @GetMapping("/api/article/list")
    public IPage<ArticleResult> selectArticleList(ArticleQuery articleQuery) {
        return articleService.selectArticleList(articleQuery);
    }

    @ApiOperation(value = "获取指定文章")
    @GetMapping("/api/article/{id}")
    public ArticleResult article(@PathVariable("id") Integer id) {
        return articleService.selectArticleResultById(id);
    }

    @ApiOperation(value = "获取点击排行前5的文章")
    @GetMapping("/api/article/top")
    public List<Article> top() {
        return articleService.list(new QueryWrapper<Article>()
                .select("article_id", "title", "view")
                .orderByDesc("view")
                .last("limit 5")
        );
    }
    @ApiOperation(value = "获取推荐的文章")
    @GetMapping("/api/article/recommend")
    public List<Article> recommend() {
        return articleService.list(new QueryWrapper<Article>()
                .select("article_id", "title")
                .eq("is_top",true)
                .orderByDesc("utime")
                .last("limit 10")
        );
    }
    @ApiOperation(value = "添加文章")
    @PostMapping("/admin/article")
    public AjaxResult add(HttpSession httpSession,
                          @RequestBody @Validated(value = {AddArticle.class}) Article article, BindingResult bindingResult) {
        AjaxResult ajaxResult = new AjaxResult();
        //验证错误
        if (bindingResult.getAllErrors().size() != 0) {
            ajaxResult.setResult(false);
            ajaxResult.setData(bindingResult.getAllErrors().get(0).toString());
            return ajaxResult;
        }
        //获得user
        User user = (User) httpSession.getAttribute("user");
        //进行业务
        article.setUserId(user.getUserId());
        boolean b = articleService.add(article);
        ajaxResult.setResult(b);
        ajaxResult.autoData();
        return ajaxResult;
    }

    @ApiOperation(value = "更新文章")
    @PutMapping("/admin/article/{id}")
    public AjaxResult update(
            @RequestBody @Validated(value = {AddArticle.class}) Article article, BindingResult bindingResult,
            @PathVariable Integer id) {
        AjaxResult ajaxResult = new AjaxResult();
        //验证错误
        if (bindingResult.getAllErrors().size() != 0) {
            ajaxResult.setResult(false);
            ajaxResult.setData(bindingResult.getAllErrors().get(0).toString());
            return ajaxResult;
        }
        //进行业务
        article.setArticleId(id);
        boolean b = articleService.updateArticle(article);
        ajaxResult.setResult(b);
        ajaxResult.autoData();
        return ajaxResult;
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping("/admin/article/{id}")
    public AjaxResult deleteById(@PathVariable Integer id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            articleService.deleteById(id);
            ajaxResult.setResult(true);
        } catch (Exception e) {
            ajaxResult.setResult(false);
        }
        ajaxResult.autoData();
        return ajaxResult;
    }
}
