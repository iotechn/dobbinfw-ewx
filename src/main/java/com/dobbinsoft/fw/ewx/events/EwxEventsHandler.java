package com.dobbinsoft.fw.ewx.events;

public interface EwxEventsHandler {

    public EwxEventType type();

    /**
     * @param cropId
     * @param agentId
     * @param rawMessage json/xml 的原始报文， handler需要在内部转换为对应的event对象
     */
    public void handle(String cropId, String agentId, String rawMessage);


}
