package com.dobbinsoft.fw.ewx.events;

import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;

public interface EwxEventsHandler {

    public EwxEventType type();

    /**
     * @param ewxAgent 当为企业级别回调时，此字段为空
     * @param rawMessage json/xml 的原始报文， handler需要在内部转换为对应的event对象
     */
    public void handle(EwxAgent ewxAgent, String rawMessage);

    public void handle(EwxCorp ewxCorp, String rawMessage);


}
