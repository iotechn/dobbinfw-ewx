package com.dobbinsoft.fw.ewx.models.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EwxTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    private String id;

    private String name;

    @JsonProperty("create_time")
    private Long createTime;

    private Integer order;

    private Boolean deleted;

}
