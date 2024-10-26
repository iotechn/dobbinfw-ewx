package com.dobbinsoft.fw.ewx.models.token;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EwxAccessToken extends EwxBaseModel {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonIgnore
    private Long expiresAt;
    // 快要过期的时间，为expireAt的4/5
    @JsonIgnore
    private Long expiresSoonAt;

    // 创建时的时间戳
    private long timestamp = System.currentTimeMillis();

    public EwxAccessToken() {}

    public Long getExpiresAt() {
        return this.expiresAt = timestamp + (1000L * expiresIn);
    }

    public Long getExpiresSoonAt() {
        return this.expiresSoonAt = timestamp + (1000L * expiresIn * 4 / 5);
    }
}
