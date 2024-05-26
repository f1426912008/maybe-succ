package com.ruoyi.dust.bean.entity;

import org.springframework.web.multipart.MultipartFile;

public class MailDetail {

    private String sendTo;

    private String subject;

    private String content;

    private MultipartFile attachment;

    public MailDetail() {
    }

    public MailDetail(String sendTo, String subject, String content, MultipartFile attachment) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "MailDetail{" +
                "sendTo='" + sendTo + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}
