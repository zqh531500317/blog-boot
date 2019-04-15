package com.zqh.blogboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqh.blogboot.mapper.CategoryMapper;
import com.zqh.blogboot.mapper.UserMapper;
import com.zqh.blogboot.pojo.Article;
import com.zqh.blogboot.mapper.ArticleMapper;
import com.zqh.blogboot.pojo.Category;
import com.zqh.blogboot.query.ArticleQuery;
import com.zqh.blogboot.query.ArticleResult;
import com.zqh.blogboot.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(Article article) {
        try {
            //校验是否存在categoryId
            Category category = categoryMapper.selectById(article.getCategoryId());
            if (category == null)
                return false;
            //添加文章
            articleMapper.add(article);
            //用户文章数+1
            userMapper.incArticleNum(article.getUserId());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public IPage<ArticleResult> selectArticleList(ArticleQuery articleQuery) {
        //获取总数
        long count = articleMapper.count(articleQuery);
        IPage<ArticleResult> page = new Page<>();
        List<ArticleResult> articleResults = articleMapper.selectArticleList(articleQuery);
        page.setRecords(articleResults)
                .setCurrent(articleQuery.getCurrent())
                .setTotal(count);
        return page;
    }

    @Override
    public Article selectArticleById(Integer id) {
        return articleMapper.selectArticleById(id);
    }

    @Override
    public ArticleResult selectArticleResultById(Integer id) {
        articleMapper.incView(id);
        return articleMapper.selectArticleResultById(id);
    }

    @Override
    public void deleteById(Integer id) {
        Article article = articleMapper.selectArticleById(id);
        articleMapper.deleteById(id);
        Integer userId = article.getUserId();
        articleMapper.decArtivleNum(userId);
    }

    @Override
    public boolean updateArticle(Article article) {
        try {
            articleMapper.updateArticle(article);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
