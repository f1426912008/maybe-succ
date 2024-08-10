package com.ruoyi.dust.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.dust.bean.entity.MailDetail;

public interface MailService {

    /**
     * 发送文本邮件
     *
     * @param mailDetail 邮件实体类
     */
    void sendSimpleMail(MailDetail mailDetail);

    /**
     * 发送HTML邮件
     *
     * @param mailDetail 邮件实体类
     */
    void sendHtmlMail(MailDetail mailDetail);


    /**
     * 发送带附件的邮件
     *
     * @param mailDetail 邮件实体类
     */
    void sendAttachmentsMail(MailDetail mailDetail);

    AjaxResult contactList();
}
