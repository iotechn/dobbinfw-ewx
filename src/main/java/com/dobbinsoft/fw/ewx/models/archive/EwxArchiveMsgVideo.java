package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxArchiveMsgVideo extends EwxArchiveMsgBase {


    @JsonProperty("video")
    private VideoDTO video;

    @NoArgsConstructor
    @Data
    public static class VideoDTO {
        @JsonProperty("md5sum")
        private String md5sum;
        @JsonProperty("filesize")
        private Integer filesize;
        @JsonProperty("play_length")
        private Integer playLength;
        @JsonProperty("sdkfileid")
        private String sdkfileid;
    }
}
