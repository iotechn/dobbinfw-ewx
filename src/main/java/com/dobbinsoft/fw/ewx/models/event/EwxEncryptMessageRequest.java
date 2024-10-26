package com.dobbinsoft.fw.ewx.models.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @author wumengmeng
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EwxEncryptMessageRequest {

    @JsonProperty("ToUserName")
    private String toUserName;
    @JsonProperty("Encrypt")
    private String encrypt;
    @JsonProperty("AgentID")
    private String agentId;
}
