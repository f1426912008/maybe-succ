package com.ruoyi.dust.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.dust.bean.entity.MailDetail;
import com.ruoyi.dust.service.MailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mailUtil")
public class MailSendController {

    @Resource
    private MailService mailService;

    @PostMapping("/sendMail")
    public AjaxResult sendMail(MailDetail mailDetail) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        System.err.println(loginUser);
        mailDetail.setSendTo("1426912008@qq.com");
        mailDetail.setContent("csad");
        mailDetail.setSubject("1231");
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
}
