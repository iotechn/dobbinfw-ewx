package com.dobbinsoft.fw.ewx.models.archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EwxArchiveMsgLocation extends EwxArchiveMsgBase {


    @JsonProperty("location")
    private LocationDTO location;

    @NoArgsConstructor
    @Data
    public static class LocationDTO {
        @JsonProperty("longitude")
        private Double longitude;
        @JsonProperty("latitude")
        private Double latitude;
        @JsonProperty("address")
        private String address;
        @JsonProperty("title")
        private String title;
        @JsonProperty("zoom")
        private Integer zoom;
    }

}
