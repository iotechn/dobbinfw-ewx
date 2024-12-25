package com.dobbinsoft.fw.ewx.models.tag;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxCorpTagAttr extends EwxBaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    @JsonProperty("tag_group")
    private List<EwxTagGroup> ewxTagGroupList;


}
