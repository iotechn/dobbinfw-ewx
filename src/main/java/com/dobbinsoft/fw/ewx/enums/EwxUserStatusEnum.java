package com.dobbinsoft.fw.ewx.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EwxUserStatusEnum implements BaseEnums<Integer> {
    ACTIVE(1, "已激活"),
    DISABLE(2, "已禁用"),
    // 未激活意思是邀请了该用户，但是用户从未登录激活
    INACTIVE(4, "未激活"),
    QUIT(5, "已退出")
    ;

    private final Integer code;
    private final String msg;

}
