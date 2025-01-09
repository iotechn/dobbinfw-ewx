package com.dobbinsoft.fw.ewx.models.jssdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxJsSdkApiTicket {

    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("ticket")
    private String ticket;
    @JsonProperty("expires_in")
    private Integer expiresIn;

}
