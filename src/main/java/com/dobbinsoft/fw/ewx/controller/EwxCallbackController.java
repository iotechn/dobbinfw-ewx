package com.dobbinsoft.fw.ewx.controller;

import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.enums.EwxCorpSecretEnum;
import com.dobbinsoft.fw.ewx.events.EwxEventProcessor;
import com.dobbinsoft.fw.ewx.models.event.EwxEncryptMessageRequest;
import com.dobbinsoft.fw.ewx.models.event.EwxCallbackEncryptRequest;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 需要手动import这个controller
 */
@Slf4j
@RestController
@RequestMapping("/cb/ewx")
public class EwxCallbackController {

    @Autowired
    private EwxEventProcessor ewxEventProcessor;

    // 验证AGENT回调URL
    @GetMapping("/{corpId}/{agentId}")
    @ResponseBody
    public String verifyUrl(
            @PathVariable("corpId") String corpId,
            @PathVariable("agentId") String agentId,
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        // 框架不会自动下划线转驼峰，所以手动映射下
        EwxCallbackEncryptRequest request = new EwxCallbackEncryptRequest();
        request.setMsgSignature(msgSignature);
        request.setTimestamp(timestamp);
        request.setNonce(nonce);
        request.setEncryptStr(echostr);
        return ewxEventProcessor.verifyNotifyUrl(corpId, agentId, request);
    }

    @PostMapping("/{corpId}/{agentId}")
    @ResponseBody
    public Object handleEvent(
            @PathVariable("corpId") String corpId,
            @PathVariable("agentId") String agentId,
            @RequestBody String xmlResult,
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce) {
        // 框架不会自动下划线转驼峰，所以手动映射下
        EwxCallbackEncryptRequest verifyRequest = new EwxCallbackEncryptRequest();
        verifyRequest.setMsgSignature(msgSignature);
        verifyRequest.setTimestamp(timestamp);
        verifyRequest.setNonce(nonce);
        EwxEncryptMessageRequest messageRequest = JacksonXmlUtil.parseObject(xmlResult, EwxEncryptMessageRequest.class);
        if (messageRequest == null) {
            log.error("[EWX] 回调报文不正确 requestBody={}", xmlResult);
            return "failed";
        }
        verifyRequest.setEncryptStr(messageRequest.getEncrypt());
        return ewxEventProcessor.routeEvent(corpId, agentId, verifyRequest, messageRequest);
    }

    // 验证会话归档回调URL
    @GetMapping("/{corpId}/archive")
    public String verifyArchiveUrl(
            @PathVariable("corpId") String corpId,
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        // 框架不会自动下划线转驼峰，所以手动映射下
        EwxCallbackEncryptRequest request = new EwxCallbackEncryptRequest();
        request.setMsgSignature(msgSignature);
        request.setTimestamp(timestamp);
        request.setNonce(nonce);
        request.setEncryptStr(echostr);
        return ewxEventProcessor.verifyCorpNotifyUrl(corpId, EwxCorpSecretEnum.ARCHIVE, request);
    }

    @PostMapping("/{corpId}/archive")
    public Object handleArchiveEvent(
            @PathVariable("corpId") String corpId,
            @RequestBody String xmlResult,
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce) {
        EwxCallbackEncryptRequest verifyRequest = new EwxCallbackEncryptRequest();
        verifyRequest.setMsgSignature(msgSignature);
        verifyRequest.setTimestamp(timestamp);
        verifyRequest.setNonce(nonce);
        EwxEncryptMessageRequest messageRequest = JacksonXmlUtil.parseObject(xmlResult, EwxEncryptMessageRequest.class);
        if (messageRequest == null) {
            log.error("[EWX] 回调报文不正确 requestBody={}", xmlResult);
            return "failed";
        }
        verifyRequest.setEncryptStr(messageRequest.getEncrypt());
        return ewxEventProcessor.routeCorpEvent(corpId, EwxCorpSecretEnum.ARCHIVE, verifyRequest, messageRequest);
    }


}
