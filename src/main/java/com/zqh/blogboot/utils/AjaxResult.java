package com.zqh.blogboot.utils;

import lombok.Data;

@Data
public class AjaxResult {
    private Boolean result;
    private String data;

    /**
     * 根据result自动生成data
     * 当懒得指定data是可使用
     */
    public void autoData(){
        if(result!=null){
            if (result){
                data="操作成功";
            }else {
                data="操作失败";
            }
        }
    }
}
