package com.zqh.blogboot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserQuery {
    private Integer offset;
    private Integer limit;
    private String username;
    private Integer role;
    private Date datemax;
    private Date datemin;
    private Boolean locked;
}
