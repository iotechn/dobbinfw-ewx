package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUserWechatChannel implements Serializable {
    @Serial
    private static final long serialVersionUID = 3699946412447128969L;

    private String nickname;
    private Integer status;

}
