package com.dobbinsoft.fw.ewx.events;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EwxEventType implements BaseEnums<String> {

    CHANGE_CONTACT_EVENT("change_contact", "企业微信用户更新"),
    CHANGE_EXTERNAL_CONTACT("change_external_contact", "外部联系人更新"),
    KF_MSG_OR_EVENT("kf_msg_or_event", "客服消息或事件"),
    MSG_AUDIT_NOTIFY("msgaudit_notify", "会话归档新消息事件"),
    ;

    private final String code;

    private final String msg;

}
