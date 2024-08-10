package com.ruoyi.dust.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.dust.bean.entity.MailDetail;
import com.ruoyi.dust.bean.po.MbMailContact;
import com.ruoyi.dust.mapper.MbMailContactMapper;
import com.ruoyi.dust.service.MailService;
import com.ruoyi.dust.utils.MultipartFileToFile;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    /**
     * 配置文件中发送者邮箱
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MbMailContactMapper mbMailContactMapper;

    /**
     * 简单文本邮件
     *
     * @param mailDetail 邮件实体类
     */
    @Override
    public void sendSimpleMail(MailDetail mailDetail) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(mailDetail.getSendTo());
        //邮件主题
        message.setSubject(mailDetail.getSubject());
        //邮件内容
        message.setText(mailDetail.getContent());
        //发送邮件
        javaMailSender.send(message);
    }

    /**
     * html邮件
     *
     * @param mailDetail 邮件实体类
     */
    @Override
    public void sendHtmlMail(MailDetail mailDetail) {
        //获取MimeMessage对象
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(mailDetail.getSendTo());
            //邮件主题
            message.setSubject(mailDetail.getSubject());
            //邮件内容，html格式
            messageHelper.setText(mailDetail.getContent(), true);
            //发送
            javaMailSender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     *
     * @param mailDetail 邮件实体类
     */
    @Override
    public void sendAttachmentsMail(MailDetail mailDetail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MultipartFile attachment = mailDetail.getAttachment();
        File fileToFile = null;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            fileToFile = MultipartFileToFile.multipartFileToFile(attachment);

            FileSystemResource file = new FileSystemResource(fileToFile);

            helper.setFrom(from);
            helper.setTo(mailDetail.getSendTo());
            helper.setSubject(mailDetail.getSubject());
            helper.setText(mailDetail.getContent(), true);
            helper.addAttachment(attachment.getName(), file);

            javaMailSender.send(message);
            log.info("邮件已经发送！");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MultipartFileToFile.deleteTempFile(fileToFile);
        }
    }

    @Override
    public AjaxResult contactList() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<MbMailContact> list = mbMailContactMapper.selectByUserId(userId);
        return AjaxResult.success(list);
    }

}
