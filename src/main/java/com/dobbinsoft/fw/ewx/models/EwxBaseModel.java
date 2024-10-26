package com.dobbinsoft.fw.ewx.models;

import com.dobbinsoft.fw.ewx.exception.EwxException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EwxBaseModel {

    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public void checkError() throws EwxException {
        if (errcode != null && errcode != 0) {
            throw new EwxException(errcode, errmsg);
        }
    }

}
