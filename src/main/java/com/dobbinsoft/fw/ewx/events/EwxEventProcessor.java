package com.dobbinsoft.fw.ewx.events;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.enums.EwxCorpSecretEnum;
import com.dobbinsoft.fw.ewx.events.model.EwxEventModel;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;
import com.dobbinsoft.fw.ewx.models.event.EwxCallbackEncryptRequest;
import com.dobbinsoft.fw.ewx.models.event.EwxEncryptMessageRequest;
import com.dobbinsoft.fw.ewx.utils.WXBizMsgCrypt;
import com.dobbinsoft.fw.support.utils.CollectionUtils;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EwxEventProcessor implements ApplicationContextAware {


    private Map<EwxEventType, List<EwxEventsHandler>> handlerMap;

    @Autowired
    private EwxClient ewxClient;

    @Autowired(required = false)
    private EwxEventBeforeHandle ewxEventBeforeHandle;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Object ewxEventHandleRouter = applicationContext.getBean("ewxEventHandleRouter");
        if (ewxEventHandleRouter instanceof Map) {
            this.handlerMap = (Map<EwxEventType, List<EwxEventsHandler>>) ewxEventHandleRouter;
        } else {
            this.handlerMap = Collections.emptyMap();
        }
    }

    /**
     *
     * @param ewxAgent 当为企业事件时，此字段为空
     * @param requestBody
     * @return
     */
    public String process(EwxCorp ewxCorp, EwxAgent ewxAgent, String requestBody) {
        EwxEventModel ewxEventModel = JacksonXmlUtil.parseObject(requestBody, EwxEventModel.class);
        if (ewxEventModel == null) {
            return "failed";
        }
        if ("event".equals(ewxEventModel.getMsgType())) {
            EwxEventType eventType = BaseEnums.getByCode(ewxEventModel.getEvent(), EwxEventType.class);
            List<EwxEventsHandler> ewxEventsHandlers = handlerMap.get(eventType);
            if (CollectionUtils.isEmpty(ewxEventsHandlers)) {
                log.info("[EWX] 系统未关注回调事件:{}", ewxEventModel.getEvent());
                return "not event";
            }
            for (EwxEventsHandler ewxEventsHandler : ewxEventsHandlers) {
                if (ewxCorp != null) {
                    if (ewxEventBeforeHandle != null) {
                        ewxEventBeforeHandle.handle(ewxCorp, requestBody, eventType);
                    }
                    try {
                        ewxEventsHandler.handle(ewxCorp, requestBody);
                    } finally {
                        if (ewxEventBeforeHandle != null) {
                            ewxEventBeforeHandle.handleFinally(ewxCorp, requestBody, eventType);
                        }
                    }
                }
                if (ewxAgent != null) {
                    if (ewxEventBeforeHandle != null) {
                        ewxEventBeforeHandle.handle(ewxAgent, requestBody, eventType);
                    }
                    try {
                        ewxEventsHandler.handle(ewxAgent, requestBody);
                    } finally {
                        if (ewxEventBeforeHandle != null) {
                            ewxEventBeforeHandle.handleFinally(ewxAgent, requestBody, eventType);
                        }
                    }
                }
            }
            return "success";
        }
        return "not event";
    }



    /**
     * 校验AGENT回调URL
     * @param corpId
     * @param agentId
     * @param request
     * @return
     */
    public String verifyNotifyUrl(String corpId, String agentId, EwxCallbackEncryptRequest request) {
        EwxAgent ewxAgent = ewxClient.getAgent(corpId, agentId);
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(ewxAgent.getToken(), ewxAgent.getAesKey(), ewxAgent.getCorpId());
            return wxBizMsgCrypt.verifyAndDecrypt(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEncryptStr());
        } catch (ServiceException e) {
            log.error("[EWX] 验证回调URL异常 message={}, request={}", e.getMessage(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }
    }

    /**
     * 将Agent 回调消息路由到某个Agent上
     * @param corpId
     * @param agentId
     * @param request
     * @param encryptMessageRequest
     * @return
     */
    public String routeEvent(String corpId, String agentId, EwxCallbackEncryptRequest request, EwxEncryptMessageRequest encryptMessageRequest) {
        try {
            EwxAgent ewxAgent = ewxClient.getAgent(corpId, agentId);
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(ewxAgent.getToken(), ewxAgent.getAesKey(), ewxAgent.getCorpId());
            String decryptXmlRequest = wxBizMsgCrypt.verifyAndDecrypt(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEncryptStr());
            log.info("[EWX] 回调请求报文 request={}", decryptXmlRequest);
            return this.process(null, ewxAgent, decryptXmlRequest);
        } catch (ServiceException e) {
            log.error("[EWX] 回调请求报文 异常 message={}, request={}", e.getMessage(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }
    }

    /**
     * 校验CORP回调URL
     * @param corpId
     * @param secretEnum
     * @param request
     * @return
     */
    public String verifyCorpNotifyUrl(String corpId, EwxCorpSecretEnum secretEnum, EwxCallbackEncryptRequest request) {
        EwxCorp ewxCorp = ewxClient.getCorp(corpId);
        String token;
        String aesKey;
        if (secretEnum == EwxCorpSecretEnum.ARCHIVE) {
            token = ewxCorp.getArchiveToken();
            aesKey = ewxCorp.getArchiveAesKey();
        } else {
            // 此代码不可能执行
            throw new RuntimeException("不支持的secret类型");
        }
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, aesKey, corpId);
            return wxBizMsgCrypt.verifyAndDecrypt(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEncryptStr());
        } catch (ServiceException e) {
            log.error("[EWX] 验证回调URL异常 message={}, secretEnum={}, request={}", e.getMessage(), secretEnum.name(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }

    }
    /**
     * 将Corp 回调消息路由到某个Corp上
     * @param corpId
     * @param secretEnum
     * @param request
     * @param encryptMessageRequest
     * @return
     */
    public String routeCorpEvent(String corpId, EwxCorpSecretEnum secretEnum, EwxCallbackEncryptRequest request, EwxEncryptMessageRequest encryptMessageRequest) {
        EwxCorp ewxCorp = ewxClient.getCorp(corpId);
        String token;
        String aesKey;
        if (secretEnum == EwxCorpSecretEnum.ARCHIVE) {
            token = ewxCorp.getArchiveToken();
            aesKey = ewxCorp.getArchiveAesKey();
        } else {
            // 此代码不可能执行
            throw new RuntimeException("不支持的secret类型");
        }
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, aesKey, corpId);
            String decryptXmlRequest = wxBizMsgCrypt.verifyAndDecrypt(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEncryptStr());
            log.info("[EWX] 回调请求报文 request={}", decryptXmlRequest);
            return this.process(ewxCorp, null, decryptXmlRequest);
        } catch (ServiceException e) {
            log.error("[EWX] 回调请求报文 异常 message={}, request={}", e.getMessage(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }
    }


}
