package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class EwxArchiveRawData {


    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("chatdata")
    private List<ChatdataDTO> chatdata;

    @NoArgsConstructor
    @Data
    public static class ChatdataDTO {
        @JsonProperty("seq")
        private Integer seq;
        @JsonProperty("msgid")
        private String msgid;
        @JsonProperty("publickey_ver")
        private Integer publickeyVer;
        @JsonProperty("encrypt_random_key")
        private String encryptRandomKey;
        @JsonProperty("encrypt_chat_msg")
        private String encryptChatMsg;
    }
}
