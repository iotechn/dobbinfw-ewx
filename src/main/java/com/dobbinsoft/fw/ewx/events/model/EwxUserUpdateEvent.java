package com.dobbinsoft.fw.ewx.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * 三种类型的通知聚合 通过ChangeType字段来确认具体消息类型
 *
 * 成员变更通知 create_user
 * * 【重要】对于2022年8月15号后通讯录助手新配置或修改的回调url，成员属性只回调UserID/Department两个字段
 *
 * 部门变更通知 change_contact
 * * 【重要】对于2022年8月15号后通讯录助手新配置或修改的回调url，部门属性只回调Id/ParentId两个字段
 *
 * 标签变更通知 change_contact
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class EwxUserUpdateEvent {

    @JsonProperty("ToUserName")
    private String toUserName;

    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("CreateTime")
    private long createTime;

    @JsonProperty("MsgType")
    private String msgType;

    @JsonProperty("Event")
    private String event;

    @JsonProperty("ChangeType")
    private String changeType;

    @JsonProperty("UserID")
    private String userId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Department")
    private int[] department;

    @JsonProperty("IsLeaderInDept")
    private Integer isLeaderInDept;

    @JsonProperty("DirectLeader")
    private Integer directLeader;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("BizMail")
    private String bizMail;

    @JsonProperty("WeixinId")
    private String weixinId;

    @JsonProperty("Avatar")
    private String avatar;

    @JsonProperty("Status")
    private int status;

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("ParentId")
    private Integer parentId;

    @JsonProperty("Order")
    private Integer order;

    @JsonProperty("TagId")
    private Integer tagId;

    @JsonProperty("AddUserItems")
    private String addUserItems;

    @JsonProperty("delUserItems")
    private String DelUserItems;

    @JsonProperty("AddPartyItems")
    private String addPartyItems;

    @JsonProperty("delPartyItems")
    private String DelPartyItems;
}
