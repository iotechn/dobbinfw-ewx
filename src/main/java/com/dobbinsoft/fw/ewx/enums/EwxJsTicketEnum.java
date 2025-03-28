package com.dobbinsoft.fw.ewx.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EwxJsTicketEnum implements BaseEnums<Integer> {
    CORP(1, "企业"),
    AGENT(2, "应用"),
    ;

    private final Integer code;
    private final String msg;

}
