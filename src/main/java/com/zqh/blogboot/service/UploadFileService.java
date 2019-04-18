package com.zqh.blogboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.blogboot.pojo.UploadFile;

public interface UploadFileService extends IService<UploadFile> {
    boolean deleteFile(Integer id, String visitpath, String filepath);
}
