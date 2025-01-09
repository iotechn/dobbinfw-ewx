package com.dobbinsoft.fw.ewx.models.jssdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EwxJsSdkConfigAgentResult {

    @JsonProperty("corpid")
    private String corpId;

    @JsonProperty("agentid")
    private String agentId;

    private String timestamp;

    private String nonceStr;

    private String signature;

}