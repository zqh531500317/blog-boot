package com.zqh.blogboot.utils.EchartsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转化器，输入List<Count>集合，转化为Map<String,Object>
 */
public class EchartConvertor {
    public static Map<String,Object> convert(List<Count> list){
        Map<String,Object> map=new HashMap<>();
        List<String> name=new ArrayList<>();
        List<Integer> data=new ArrayList<>();
        for (Count count : list) {
            name.add(count.getName());
            data.add(count.getValue());
        }
        map.put("name",name);
        map.put("data",data);
        return map;
    }
}
