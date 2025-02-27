package com.dobbinsoft.fw.ewx.models.external;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EwxExternalContactIdAttr extends EwxBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -1992663324207669440L;

    @JsonProperty("external_userid")
    private String[] externalUserid;
}
