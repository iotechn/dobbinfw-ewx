package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxArchiveMsgText extends EwxArchiveMsgBase {


    @JsonProperty("text")
    private TextDTO text;

    @NoArgsConstructor
    @Data
    public static class TextDTO {
        @JsonProperty("content")
        private String content;
    }
}
