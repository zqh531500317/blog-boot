package com.zqh.blogboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqh.blogboot.mapper.UploadFileMapper;
import com.zqh.blogboot.pojo.UploadFile;
import com.zqh.blogboot.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements UploadFileService {
    @Override
    public boolean deleteFile(Integer id, String visitpath, String filepath) {
        UploadFile uploadFile = new UploadFile();
        uploadFile = uploadFile.setId(id).selectById();
        String finalpath = uploadFile.getPath().replace(visitpath, filepath);
        File file = new File(finalpath);
        //从数据库删除
        uploadFile.deleteById();
        //删除文件
        return file.delete();
    }
}
