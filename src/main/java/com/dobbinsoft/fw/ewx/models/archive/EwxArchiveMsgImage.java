package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EwxArchiveMsgImage extends EwxArchiveMsgBase {

    @JsonProperty("image")
    private ImageDTO image;

    @NoArgsConstructor
    @Data
    public static class ImageDTO {
        @JsonProperty("md5sum")
        private String md5sum;
        @JsonProperty("filesize")
        private Integer filesize;
        @JsonProperty("sdkfileid")
        private String sdkfileid;
    }

}
