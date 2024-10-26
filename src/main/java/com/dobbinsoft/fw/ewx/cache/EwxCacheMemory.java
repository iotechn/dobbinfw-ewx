package com.dobbinsoft.fw.ewx.cache;

import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
public class EwxCacheMemory implements EwxCache {

    private final Map<String, EwxAccessToken> cacheMap = new ConcurrentHashMap<>();

    @Override
    public EwxAccessToken getToken(String corpId, String agentId) {
        EwxAccessToken ewxAccessToken = cacheMap.get(concatCacheKey(corpId, agentId));
        if (ewxAccessToken == null) {
            return null;
        }
        Long expiresSoonAt = ewxAccessToken.getExpiresSoonAt();
        if (System.currentTimeMillis() > expiresSoonAt) {
            return null;
        }
        return ewxAccessToken;
    }

    @Override
    public void setToken(String corpId, String agentId, EwxAccessToken ewxAccessToken) {
        this.cacheMap.put(concatCacheKey(corpId, agentId), ewxAccessToken);
    }

    private String concatCacheKey(String corpId, String agentId) {
        return corpId + "---" + agentId;
    }
}
