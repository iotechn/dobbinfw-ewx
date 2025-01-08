package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;

public class AbstractCustomerMessageHandler implements EwxEventsHandler {
    @Override
    public EwxEventType type() {
        return EwxEventType.KF_MSG_OR_EVENT;
    }

    @Override
    public void handle(EwxAgent ewxAgent, String rawMessage) {
        //TODO
    }

    @Override
    public void handle(EwxCorp ewxCorp, String rawMessage) {
        // 企业事件，不处理
    }
}
