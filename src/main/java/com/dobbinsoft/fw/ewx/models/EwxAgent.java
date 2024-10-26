package com.dobbinsoft.fw.ewx.models;

public interface EwxAgent {
    public String getCorpId();

    public String getAgentId();

    public String getSecret();

    public String getAesKey();

    public String getToken();

    public String getProxy();


    public default String cacheKey() {
        return getCorpId() + "---" + getAgentId();
    }

}
