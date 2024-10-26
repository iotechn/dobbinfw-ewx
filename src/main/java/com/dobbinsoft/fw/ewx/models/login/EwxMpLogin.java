package com.dobbinsoft.fw.ewx.models.login;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxMpLogin extends EwxBaseModel {
    @JsonProperty("corpid")
    private String corpid;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("session_key")
    private String sessionKey;
}
