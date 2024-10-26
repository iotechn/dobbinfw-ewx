package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class EwxUserExternalMiniprogram implements Serializable {

    @Serial
    private static final long serialVersionUID = 7523699319514181217L;
    private String appid;
    private String pagepath;
    private String title;

}
