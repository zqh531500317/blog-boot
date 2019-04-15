package com.zqh.blogboot.service.impl;

import com.zqh.blogboot.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceImplTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void sendSimpleEmail() {
        emailService.sendSimpleEmail("531500317@qq.com", "sendSimpleEmail", "hello !!!");
    }

    @Test
    public void sendHtmlEmail() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        emailService.sendHtmlEmail("531500317@qq.com", "this is html mail", content);
    }

    @Test
    public void sendAttachmentsEmail() {
        String filePath = "G:\\upload\\img\\50f07c02-eca7-4218-913d-1ea308abe20d.png";
        emailService.sendAttachmentsEmail("531500317@qq.com", "主题：带附件的邮件", "收到附件，请查收！", filePath);
    }

    @Test
    public void sendInlineResourceEmail() {
        String rscId = "001";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "G:\\upload\\img\\50f07c02-eca7-4218-913d-1ea308abe20d.png";

        emailService.sendInlineResourceEmail("531500317@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }
}