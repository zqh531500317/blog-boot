package com.zqh.blogboot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleQuery {
    private Integer userId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datemax;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datemin;
    private Integer current;
    private Integer pageSize;
    private Integer categoryId;
    //数据库偏移
    private int offset;
    //数据库大小限制
    private int limit;

    public Integer getCurrent() {
        if (current == null)
            current = 1;
        return current;
    }

    public Integer getPageSize() {
        if (pageSize == null || (pageSize != 10 && pageSize != 50))
            pageSize = 10;
        return pageSize;
    }

    public Integer getOffset() {
        return (getCurrent() - 1) * getPageSize();
    }

    public Integer getLimit() {
        return getPageSize();
    }
}
