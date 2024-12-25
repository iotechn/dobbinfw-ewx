package com.dobbinsoft.fw.ewx.models.message;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EwxEnterpriseMessageAttr extends EwxBaseModel {

    /**
     * 	无效或无法发送的external_userid或chatid列表
     */
    @JsonProperty("fail_list")
    private List<String> failList;

    /**
     * 企业群发消息的id，可用于获取群发消息发送结果
     */
    @JsonProperty("msgid")
    private String msgId;
}
