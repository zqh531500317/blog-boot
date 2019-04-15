package com.zqh.blogboot.controller;

import com.zqh.blogboot.utils.FileUtil;
import com.zqh.blogboot.utils.WangEditor3Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@Api("上传操作api")
@PropertySource("classpath:config/file.properties")
public class UploadController {
    @Value("${upload.path}")
    private String filepath;
    @Value("${visit.path}")
    private String visitpath;
    private static Logger logger = Logger.getLogger(UploadController.class.getName());
    @ApiOperation(value = "上传文件")
    @PostMapping("/api/upload")
    public WangEditor3Result upload(@RequestParam(value = "file") MultipartFile file) {
        WangEditor3Result wangEditor3Result = new WangEditor3Result();
        try {
            if (file.getSize() > 0) {
                //将每个月文件分类存储不同目录
                String pack = new SimpleDateFormat("yyyy.MM").format(new Date());
                //获取后缀名
                String extension = FileUtil.getExtension(file.getOriginalFilename());
                //产生随机的文件名
                String filename = UUID.randomUUID() + "." + extension;
                //生成最终的存储路径
                String finalPath = FileUtil.getFinalPath(filepath + pack + "/", filename);
                File f = new File(finalPath);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdir();
                }
                file.transferTo(f);//上传
                logger.info("上传了文件，目录：" + finalPath);
                //返回访问路径
                wangEditor3Result.setUrl(visitpath  + pack + "/" + filename);
                return wangEditor3Result;
            } else {
                return wangEditor3Result;
            }

        } catch (IOException e) {
            return wangEditor3Result;
        }
    }
}
