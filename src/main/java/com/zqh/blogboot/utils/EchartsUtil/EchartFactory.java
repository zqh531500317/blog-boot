package com.zqh.blogboot.utils.EchartsUtil;

import java.util.List;
import java.util.Map;

/**
 * echarts工厂类，提供简单的图表样式
 */
public class EchartFactory {

    public static Map<String, Object> createBar(List<Count> list) {

        return EchartConvertor.convert(list);
    }

    public static List<Count> createPie(List<Count> list) {
        return list;
    }

    public static Map<String, Object> createline(List<Count> list) {
        return EchartConvertor.convert(list);
    }
}
