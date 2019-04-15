package com.zqh.blogboot.mapper;

import com.zqh.blogboot.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqh.blogboot.query.ArticleQuery;
import com.zqh.blogboot.query.ArticleResult;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqh
 * @since 2019-01-21
 */
public interface ArticleMapper extends BaseMapper<Article> {
    void add(Article article);

    List<ArticleResult> selectArticleList(ArticleQuery articleQuery);
    long count(ArticleQuery articleQuery);
    void decArtivleNum(Integer userId);

    void updateArticle(Article article);

    Article selectArticleById(Integer id);

    ArticleResult selectArticleResultById(Integer id);
    void incView(Integer id);
}
