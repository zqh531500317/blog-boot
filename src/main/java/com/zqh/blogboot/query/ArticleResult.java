package com.zqh.blogboot.query;

import com.zqh.blogboot.pojo.Article;
import com.zqh.blogboot.pojo.Category;
import lombok.Data;

@Data
public class ArticleResult {
    private Integer articleId;
    private Article article;
    private Category category;
    private String nickname;
}
