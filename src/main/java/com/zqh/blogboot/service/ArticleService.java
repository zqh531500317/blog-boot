package com.zqh.blogboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqh.blogboot.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.blogboot.query.ArticleQuery;
import com.zqh.blogboot.query.ArticleResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface ArticleService extends IService<Article> {
    boolean add(Article article);

    IPage<ArticleResult> selectArticleList(ArticleQuery articleQuery);

    void deleteById(Integer id);

    boolean updateArticle(Article article);

    Article selectArticleById(Integer id);
    ArticleResult selectArticleResultById(Integer id);
}
