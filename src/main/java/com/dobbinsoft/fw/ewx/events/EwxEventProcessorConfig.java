package com.dobbinsoft.fw.ewx.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class EwxEventProcessorConfig {

    @Bean("ewxEventHandleRouter")
    public Map<EwxEventType, EwxEventsHandler> ewxEventHandleRouter(List<EwxEventsHandler> ewxEventsHandlers) {
        return ewxEventsHandlers.stream().collect(Collectors.toMap(EwxEventsHandler::type, v -> v));
    }

    @Bean
    public EwxEventProcessor ewxEventProcessor() {
        return new EwxEventProcessor();
    }


}