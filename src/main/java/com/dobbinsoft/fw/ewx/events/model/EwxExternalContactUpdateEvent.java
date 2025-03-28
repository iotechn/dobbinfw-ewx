package com.dobbinsoft.fw.ewx.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class EwxExternalContactUpdateEvent {

    @JsonProperty("ToUserName")
    private String toUserName;
    @JsonProperty("FromUserName")
    private String fromUserName;
    @JsonProperty("CreateTime")
    private String createTime;
    @JsonProperty("MsgType")
    private String msgType;
    @JsonProperty("Event")
    private String event;
    @JsonProperty("ChangeType")
    private ChangeType changeType;
    @JsonProperty("UserID")
    private String userID;
    @JsonProperty("ExternalUserID")
    private String externalUserID;
    @JsonProperty("State")
    private String state;
    @JsonProperty("WelcomeCode")
    private String welcomeCode;
    @JsonProperty("FailReason")
    private String failReason;
    @JsonProperty("ChatId")
    private String chatId;


    public enum ChangeType {
        msg_audit_approved,
        add_external_contact, // 添加企业客户事件
        edit_external_contact, // 编辑企业客户事件
        add_half_external_contact, // 外部联系人免验证添加成员事件
        del_external_contact, // 删除企业客户事件 (SA主动删客)
        del_follow_user, // 删除跟进成员事件(客人删SA)
        transfer_fail, // 客户接替失败事件
        change_external_chat, // 客户群创建事件
        update,
        create,
        delete,
        shuffle

    }

}
