package com.dobbinsoft.fw.ewx.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "xml")
public class EwxTagUpdateEvent {
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
