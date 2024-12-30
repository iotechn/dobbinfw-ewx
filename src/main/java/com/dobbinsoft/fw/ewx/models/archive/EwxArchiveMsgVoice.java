package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxArchiveMsgVoice extends EwxArchiveMsgBase {


    @JsonProperty("voice")
    private VoiceDTO voice;

    @NoArgsConstructor
    @Data
    public static class VoiceDTO {
        @JsonProperty("md5sum")
        private String md5sum;
        @JsonProperty("voice_size")
        private Integer voiceSize;
        @JsonProperty("play_length")
        private Integer playLength;
        @JsonProperty("sdkfileid")
        private String sdkfileid;
    }
}
