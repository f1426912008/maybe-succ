package com.ruoyi.dust.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 邮件联系人表
 * @TableName mb_mail_contact
 */
@Data
public class MbMailContact implements Serializable {
    /**
     * 联系人id
     */
    private Integer contactId;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 邮件地址
     */
    private String mailAddr;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 所属用户ID
     */
    private Integer userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private String isDel;

    /**
     * 是否收藏
     */
    private String isCollect;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}