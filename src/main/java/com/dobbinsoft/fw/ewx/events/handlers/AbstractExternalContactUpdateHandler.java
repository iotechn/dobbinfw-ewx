package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;
import com.dobbinsoft.fw.ewx.events.model.EwxExternalContactUpdateEvent;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractExternalContactUpdateHandler implements EwxEventsHandler {

    @Autowired
    private EwxClient ewxClient;

    @Override
    public EwxEventType type() {
        return EwxEventType.CHANGE_EXTERNAL_CONTACT;
    }

    @Override
    public void handle(EwxAgent ewxAgent, String rawMessage) {
        EwxExternalContactUpdateEvent ewxUserUpdateEvent = JacksonXmlUtil.parseObject(rawMessage, EwxExternalContactUpdateEvent.class);
        if (Objects.isNull(ewxUserUpdateEvent)) {
            return;
        }

    }

    @Override
    public void handle(EwxCorp ewxCorp, String rawMessage) {

    }

    public abstract void handle(EwxAgent ewxAgent, EwxExternalContactUpdateEvent updateEvent);

}
