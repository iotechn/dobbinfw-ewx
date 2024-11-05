package com.dobbinsoft.fw.ewx.controller;

import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.models.event.EwxEncryptMessageRequest;
import com.dobbinsoft.fw.ewx.models.event.EwxUrlVerifyRequest;
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
    private EwxClient ewxClient;

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
        EwxUrlVerifyRequest request = new EwxUrlVerifyRequest();
        request.setMsgSignature(msgSignature);
        request.setTimestamp(timestamp);
        request.setNonce(nonce);
        request.setEchostr(echostr);
        return ewxClient.verifyNotifyUrl(corpId, agentId, request);
    }

    @PostMapping("/{corpId}/{agentId}")
    @ResponseBody
    public Object handleEvent(
            @PathVariable("corpId") String corpId,
            @PathVariable("agentId") String agentId,
            @RequestBody String xmlResult,
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        // 框架不会自动下划线转驼峰，所以手动映射下
        EwxUrlVerifyRequest verifyRequest = new EwxUrlVerifyRequest();
        verifyRequest.setMsgSignature(msgSignature);
        verifyRequest.setTimestamp(timestamp);
        verifyRequest.setNonce(nonce);
        verifyRequest.setEchostr(echostr);
        EwxEncryptMessageRequest messageRequest = JacksonXmlUtil.parseObject(xmlResult, EwxEncryptMessageRequest.class);
        if (messageRequest == null) {
            log.error("[EWX] 回调报文不正确 requestBody={}", xmlResult);
            return "failed";
        }
        return ewxClient.routeEvent(corpId, agentId, verifyRequest, messageRequest);
    }

}
