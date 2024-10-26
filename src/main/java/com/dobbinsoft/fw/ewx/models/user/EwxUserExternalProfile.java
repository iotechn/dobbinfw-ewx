package com.dobbinsoft.fw.ewx.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class EwxUserExternalProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = -5366249915333421300L;

    @JsonProperty("external_corp_name")
    private String externalCorpName;
    @JsonProperty("wechatChannels")
    private EwxUserWechatChannel wechatChannels;
    @JsonProperty("external_attr")
    private List<EwxUserExternalAttr> externalAttr;

}
