package com.dobbinsoft.fw.ewx.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class EwxEventModel {
    @JsonProperty("ToUserName")
    private String toUserName;
    @JsonProperty("CreateTime")
    private String createTime;
    @JsonProperty("MsgType")
    private String msgType;
    @JsonProperty("Event")
    private String event;
    @JsonProperty("Token")
    private String token;
    @JsonProperty("OpenKfId")
    private String openKfId;
}
