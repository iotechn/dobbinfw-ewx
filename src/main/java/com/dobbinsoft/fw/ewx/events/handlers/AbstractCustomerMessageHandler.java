package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;

public class AbstractCustomerMessageHandler implements EwxEventsHandler {
    @Override
    public EwxEventType type() {
        return EwxEventType.KF_MSG_OR_EVENT;
    }

    @Override
    public void handle(String corpId, String agentId, String rawMessage) {
        //TODO
    }
}
