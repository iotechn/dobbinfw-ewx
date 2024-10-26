package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;
import com.dobbinsoft.fw.ewx.events.model.EwxUserUpdateEvent;
import com.dobbinsoft.fw.ewx.models.user.EwxUser;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserUpdateHandler implements EwxEventsHandler {

    @Autowired
    private EwxClient ewxClient;

    @Override
    public EwxEventType type() {
        return EwxEventType.CHANGE_CONTACT_EVENT;
    }

    @Override
    public void handle(String cropId, String agentId, String rawMessage) {
        EwxUserUpdateEvent ewxUserUpdateEvent = JacksonXmlUtil.parseObject(rawMessage, EwxUserUpdateEvent.class);
        // 获取UserId
        String userId = "";
        // 接到更新回调，直接查一次最新的用户
        EwxUser user = ewxClient.getUser(cropId, agentId, userId);
        latestEwxUser(user);
    }

    public abstract void latestEwxUser(EwxUser ewxUser);

}
