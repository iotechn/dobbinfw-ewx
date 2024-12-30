package com.dobbinsoft.fw.ewx.events;

public interface EwxEventsHandler {

    public EwxEventType type();

    /**
     * @param corpId
     * @param agentId 当为企业级别回调时，此字段为空
     * @param rawMessage json/xml 的原始报文， handler需要在内部转换为对应的event对象
     */
    public void handle(String corpId, String agentId, String rawMessage);


}
