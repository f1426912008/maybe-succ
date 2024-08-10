package com.ruoyi.dust.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.dust.bean.entity.MailDetail;
import com.ruoyi.dust.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mailUtil")
public class MailSendController {

    @Resource
    private MailService mailService;

    @PostMapping("/sendMail")
    public AjaxResult sendMail(@RequestBody MailDetail mailDetail) {
        if (StringUtils.isBlank(mailDetail.getSendTo())) {
            return AjaxResult.error("收件人不可为空！");
        }
        if (StringUtils.isBlank(mailDetail.getSubject())) {
            return AjaxResult.error("邮件主题不可为空！");
        }
        try {
            MultipartFile attachment = mailDetail.getAttachment();
            if (attachment != null && attachment.getSize() > 0) {
                mailService.sendAttachmentsMail(mailDetail);
            } else {
                mailService.sendSimpleMail(mailDetail);
            }
            return AjaxResult.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("发送失败");
        }
    }

    @GetMapping("/contactList")
    public AjaxResult contactList() {
        return mailService.contactList();
    }
}
