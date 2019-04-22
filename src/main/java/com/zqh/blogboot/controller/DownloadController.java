package com.zqh.blogboot.controller;

import com.zqh.blogboot.pojo.UploadFile;
import com.zqh.blogboot.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Api("下载文件api")
@Controller
public class DownloadController {
    @Value("${upload.path}")
    private String filepath;
    @Value("${visit.path}")
    private String visitpath;
    private static Logger logger = Logger.getLogger(UploadController.class.getName());
    @Autowired
    private UploadFileService uploadFileService;

    @ApiOperation(value = "管理员下载文件")
    @GetMapping("/admin/download/{id}")
    public String download(@PathVariable("id") Integer id, HttpServletResponse response) {
        UploadFile file = uploadFileService.getById(id);
        String path = file.getPath().replace(visitpath, filepath);
        String fileName = file.getName();
        File f = new File(path);
        if (f.exists()) { //判断文件父目录是否存在

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                response.setHeader("Content-Length", ""+f.length());
                os = response.getOutputStream();
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("----------file download:" + fileName);
            try {
                if (bis != null)
                    bis.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
