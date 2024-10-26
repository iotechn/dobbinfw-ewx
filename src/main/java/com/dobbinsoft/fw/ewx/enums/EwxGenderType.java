package com.dobbinsoft.fw.ewx.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EwxGenderType implements BaseEnums<Integer> {

    SECRET(0, "未知"),
    MALE(1,"男"),
    FEMALE(2,"女"),
    ;


    private final Integer code;

    private final String msg;

}
