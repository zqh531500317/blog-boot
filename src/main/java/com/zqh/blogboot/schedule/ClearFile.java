package com.zqh.blogboot.schedule;

import com.zqh.blogboot.pojo.UploadFile;
import com.zqh.blogboot.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清除过期的文件
 */
@Component
public class ClearFile {
    @Autowired
    private UploadFileService uploadFileService;
    @Value("${upload.path}")
    private String filepath;
    @Value("${visit.path}")
    private String visitpath;
    Logger logger = LoggerFactory.getLogger(ClearFile.class);

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    private void task() {
        logger.info("执行删除文件定时任务.............");
        List<UploadFile> list = uploadFileService.list();
        List<UploadFile> filterList = filter(list);
        for (UploadFile uploadFile : filterList) {
            uploadFileService.deleteFile(uploadFile.getId(), visitpath, filepath);
        }
    }

    //得到超过时间限制的文件
    private List<UploadFile> filter(List<UploadFile> list) {
        return list.stream().filter((e) -> {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.DATE, now.get(Calendar.DATE) - e.getLiveTime());
            System.out.println(now.getTime().getTime());
            System.out.println(e.getDate().getTime());
            return now.getTime().getTime() > e.getDate().getTime();
        }).collect(Collectors.toList());
    }
}
