package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class EwxArchiveMsgBase {


    @JsonProperty("msgid")
    private String msgid;
    @JsonProperty("action")
    private String action;
    @JsonProperty("from")
    private String from;
    @JsonProperty("tolist")
    private List<String> tolist;
    @JsonProperty("roomid")
    private String roomid;
    @JsonProperty("msgtime")
    private Long msgtime;
    @JsonProperty("msgtype")
    private String msgtype;


}
