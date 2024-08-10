package com.ruoyi.dust.mapper;

import com.ruoyi.dust.bean.po.MbMailContact;

import java.util.List;

/**
* @author 范亚鑫
* @description 针对表【mb_mail_contact(邮件联系人表)】的数据库操作Mapper
* @createDate 2024-06-30 00:29:22
* @Entity com.ruoyi.dust.bean.po.MbMailContact
*/
public interface MbMailContactMapper {

    List<MbMailContact> selectByUserId(Long userId);

}




