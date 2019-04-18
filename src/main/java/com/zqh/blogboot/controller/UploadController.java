package com.zqh.blogboot.controller;

import com.zqh.blogboot.pojo.UploadFile;
import com.zqh.blogboot.service.UploadFileService;
import com.zqh.blogboot.utils.FileUtil;
import com.zqh.blogboot.utils.WangEditor3Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@Api("上传操作api")
public class UploadController {
    @Value("${upload.path}")
    private String filepath;
    @Value("${visit.path}")
    private String visitpath;
    private static Logger logger = Logger.getLogger(UploadController.class.getName());
    @Autowired
    private UploadFileService uploadFileService;

    @ApiOperation(value = "上传图片")
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
                logger.info("上传了图片，目录：" + finalPath);
                //返回访问路径
                wangEditor3Result.setUrl(visitpath + pack + "/" + filename);
                return wangEditor3Result;
            } else {
                return wangEditor3Result;
            }

        } catch (IOException e) {
            return wangEditor3Result;
        }
    }

    @ApiOperation(value = "管理员上传文件")
    @PostMapping("/admin/upload")
    public void uploadFile(@RequestParam(value = "file") MultipartFile file, Integer liveTime) {
        if (file.getSize() > 0) {
            //将每个月文件分类存储不同目录
            String pack = new SimpleDateFormat("yyyy.MM").format(new Date());
            String filename = file.getOriginalFilename();
            //生成最终的存储路径
            String finalPath = FileUtil.getFinalPath(filepath + pack + "/", filename);
            File f = new File(finalPath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdir();
            }
            try {
                file.transferTo(f);//上传
                logger.info("上传了文件，目录：" + finalPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将文件名存储到数据库
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(filename)
                    .setPath(visitpath + pack + "/" + filename)
                    .setDate(new Date())
                    .setLiveTime(liveTime)
                    .insert();
        }
    }

    @ApiOperation(value = "文件列表")
    @GetMapping("/admin/upload/list")
    public List<UploadFile> list() {
        return uploadFileService.list();
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/admin/upload/{id}")
    public boolean delete(@PathVariable Integer id) {
        return uploadFileService.deleteFile(id, visitpath, filepath);
    }
}
