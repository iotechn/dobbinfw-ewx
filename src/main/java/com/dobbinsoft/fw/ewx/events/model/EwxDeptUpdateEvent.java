package com.dobbinsoft.fw.ewx.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
public class EwxDeptUpdateEvent {
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
}
