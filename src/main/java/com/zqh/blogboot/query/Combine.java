package com.zqh.blogboot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分类合并
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Combine {
    private Integer oldValue1;
    private Integer oldValue2;
    private Integer newValue;
}
