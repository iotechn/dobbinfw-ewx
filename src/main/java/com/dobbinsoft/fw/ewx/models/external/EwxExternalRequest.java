package com.dobbinsoft.fw.ewx.models.external;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxExternalRequest extends EwxBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -1992663324207669440L;

    private List<String> userid_list;

    private String cursor;

    private Integer limit;

}
