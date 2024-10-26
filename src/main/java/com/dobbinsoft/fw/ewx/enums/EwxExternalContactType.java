package com.dobbinsoft.fw.ewx.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EwxExternalContactType implements BaseEnums<Integer> {

    WECHAT(1, "微信"),
    CORP(2,"企业微信");


    private final Integer code;

    private final String msg;

}
