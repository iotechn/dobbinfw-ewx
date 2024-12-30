package com.dobbinsoft.fw.ewx.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EwxCorpSecretEnum implements BaseEnums<String> {
    ARCHIVE("archive", "会话归档"),
    ;

    private final String code;
    private final String msg;

}
