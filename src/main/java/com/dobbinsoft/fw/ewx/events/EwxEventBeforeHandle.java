package com.dobbinsoft.fw.ewx.events;

import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;

public interface EwxEventBeforeHandle {

    public void handle(EwxAgent ewxAgent, String rawMessage, EwxEventType type);
    public void handleFinally(EwxAgent ewxAgent, String rawMessage, EwxEventType type);

    public void handle(EwxCorp ewxCorp, String rawMessage, EwxEventType type);
    public void handleFinally(EwxCorp ewxCorp, String rawMessage, EwxEventType type);

}
