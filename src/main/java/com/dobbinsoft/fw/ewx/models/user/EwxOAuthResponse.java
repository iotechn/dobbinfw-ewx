package com.dobbinsoft.fw.ewx.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxOAuthResponse {

    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("user_ticket")
    private String userTicket;

}
