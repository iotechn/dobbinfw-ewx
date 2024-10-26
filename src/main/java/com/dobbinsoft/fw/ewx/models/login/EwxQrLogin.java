package com.dobbinsoft.fw.ewx.models.login;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxQrLogin extends EwxBaseModel {

    @JsonProperty("userid")
    private String userid;
}
