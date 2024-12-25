package com.dobbinsoft.fw.ewx.models.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxTagGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("create_time")
    private Long createTime;

    private Integer order;

    private Boolean deleted;

    @JsonProperty("tag")
    private List<EwxTag> ewxTagList;
}
