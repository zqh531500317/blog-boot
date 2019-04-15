package com.zqh.blogboot.service;

/**
 * 发送邮件服务
 */
public interface EmailService {
    /**
     * 发送简单邮件
     */
    void sendSimpleEmail(String to, String subject, String content);

    /**
     * 发送html格式邮件
     */
    void sendHtmlEmail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     */
    void sendAttachmentsEmail(String to, String subject, String content, String filePath);

    /**
     * 发送带静态资源的邮件
     */
    void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId);
}
