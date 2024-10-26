package com.dobbinsoft.fw.ewx.events;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import com.dobbinsoft.fw.ewx.events.model.EwxEventModel;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EwxEventProcessor {

    private final Map<EwxEventType, EwxEventsHandler> handlerMap = new HashMap<>();

    @Autowired
    public EwxEventProcessor(ApplicationContext applicationContext) {
        Map<String, EwxEventsHandler> handlers = applicationContext.getBeansOfType(EwxEventsHandler.class);
        handlers.values().forEach(handler -> {
            // 这里假设每个Handler有一个getEventType方法，返回它处理的事件类型
            EwxEventType eventType = handler.type();
            handlerMap.put(eventType, handler);
        });
    }

    public String process(String corpId, String agentId, String requestBody) {
        EwxEventModel ewxEventModel = JacksonXmlUtil.parseObject(requestBody, EwxEventModel.class);
        if (ewxEventModel == null) {
            return "failed";
        }
        if ("event".equals(ewxEventModel.getMsgType())) {
            EwxEventType eventType = BaseEnums.getByCode(ewxEventModel.getEvent(), EwxEventType.class);
            routeEvent(corpId, agentId, eventType, requestBody);
            return "success";
        }

        return "not event";
    }

    public void routeEvent(String corpId, String agentId, EwxEventType ewxEventType, String rawMessage) {
        EwxEventsHandler ewxEventsHandler = handlerMap.get(ewxEventType);
        ewxEventsHandler.handle(corpId, agentId, rawMessage);
    }


}
