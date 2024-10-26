package com.dobbinsoft.fw.ewx.models.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class EwxUrlVerifyRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3980222955979552447L;
    @JsonProperty("msg_signature")
    private String msgSignature;
    private String timestamp;
    private String nonce;
    private String echostr;
}