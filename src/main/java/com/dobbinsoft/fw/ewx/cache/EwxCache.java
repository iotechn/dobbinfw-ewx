package com.dobbinsoft.fw.ewx.cache;

import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;

public interface EwxCache {

    public EwxAccessToken getToken(String corpId, String agentId);

    public void setToken(String corpId, String agentId, EwxAccessToken ewxAccessToken);

}
