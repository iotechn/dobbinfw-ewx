package com.dobbinsoft.fw.ewx.client;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.ewx.EwxConst;
import com.dobbinsoft.fw.ewx.cache.EwxCache;
import com.dobbinsoft.fw.ewx.events.EwxEventProcessor;
import com.dobbinsoft.fw.ewx.exception.EwxException;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentAttr;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentListAttr;
import com.dobbinsoft.fw.ewx.models.event.EwxEncryptMessageRequest;
import com.dobbinsoft.fw.ewx.models.event.EwxUrlVerifyRequest;
import com.dobbinsoft.fw.ewx.models.login.EwxMpLogin;
import com.dobbinsoft.fw.ewx.models.login.EwxQrLogin;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageAttr;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageRequest;
import com.dobbinsoft.fw.ewx.models.tag.EwxCorpTagAttr;
import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;
import com.dobbinsoft.fw.ewx.models.user.*;
import com.dobbinsoft.fw.ewx.utils.WXBizMsgCrypt;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EwxClientImpl implements EwxClient {

    @Autowired
    private EwxCache ewxCache;

    @Autowired
    private EwxEventProcessor ewxEventProcessor;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    // Key corpId + "---" + agentId
    private final Map<String, EwxAgent> agentMap = new ConcurrentHashMap<>();

    @Override
    public void addAgent(EwxAgent agent) {
        agentMap.put(agent.cacheKey(), agent);
    }

    @Override
    public EwxAccessToken getEwxToken(String corpId, String agentId) {
        EwxAccessToken token = ewxCache.getToken(corpId, agentId);
        if (token != null) {
            return token;
        }
        synchronized (this) {
            EwxAccessToken tokenDoubleCheck = ewxCache.getToken(corpId, agentId);
            if (tokenDoubleCheck != null) {
                return tokenDoubleCheck;
            }
            // 重新获取Token
            EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
            if (ewxAgent == null) {
                throw new EwxException(-1, "Agent尚未注册，请调用addAgent方法添加");
            }
            String secret = ewxAgent.getSecret();
            EwxAccessToken ewxAccessToken = proxyGet(EwxConst.EWX_ACCESS_TOKEN_URL.formatted(corpId, secret), ewxAgent, EwxAccessToken.class);
            ewxCache.setToken(corpId, agentId, ewxAccessToken);
            return ewxAccessToken;
        }
    }

    @Override
    public EwxQrLogin qrLogin(String corpId, String agentId, String code) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        return proxyGet(EwxConst.USER_QR_LOGIN_URL.formatted(getToken(ewxAgent), code), ewxAgent, EwxQrLogin.class);
    }

    @Override
    public EwxMpLogin mpLogin(String corpId, String agentId, String code) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        return proxyGet(EwxConst.JS_CODE_TO_SESSION.formatted(getEwxToken(corpId, agentId).getAccessToken(), code), ewxAgent, EwxMpLogin.class);
    }

    @Override
    public EwxUser getUser(String corpId, String agentId, String userId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        return proxyGet(EwxConst.USER_GET_URL.formatted(ewxAgent, userId), ewxAgent, EwxUser.class);
    }

    @Override
    public String verifyNotifyUrl(String corpId, String agentId, EwxUrlVerifyRequest request) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(ewxAgent.getToken(), ewxAgent.getAesKey(), ewxAgent.getCorpId());
            return wxBizMsgCrypt.verifyURL(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEchostr());
        } catch (ServiceException e) {
            log.error("[EWX] 验证回调URL异常 message={}, request={}", e.getMessage(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }
    }

    @Override
    public String routeEvent(String corpId, String agentId, EwxUrlVerifyRequest request, EwxEncryptMessageRequest encryptMessageRequest) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(ewxAgent.getToken(), ewxAgent.getAesKey(), ewxAgent.getCorpId());
            String decryptXmlRequest = wxBizMsgCrypt.verifyURL(request.getMsgSignature(), request.getTimestamp(), request.getNonce(), request.getEchostr());
            log.info("[EWX] 回调请求报文 request={}", decryptXmlRequest);
            return ewxEventProcessor.process(corpId, agentId, decryptXmlRequest);
        } catch (ServiceException e) {
            log.error("[EWX] 验证回调URL异常 message={}, request={}", e.getMessage(), JacksonUtil.toJSONString(request));
            return e.getMessage();
        }
    }


    private <T> T proxyPost(String url, EwxAgent ewxAgent, Object request, Class<T> clazz) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String jsonBody = JacksonUtil.toJSONString(request);
            RequestBody body = RequestBody.create(jsonBody, mediaType);

            String proxy = ewxAgent.getProxy();
            if (StringUtils.isNotEmpty(proxy)) {
                url = url.replace(EwxConst.EWX_BASE_URL_PLACEHOLDER, proxy);
            } else {
                url = url.replace(EwxConst.EWX_BASE_URL_PLACEHOLDER, EwxConst.EWX_BASE_DEFAULT_URL);
            }
            log.info("[EWX] Post: url={},body={}", url, jsonBody);
            String responseJson = Objects.requireNonNull(okHttpClient.newCall(new Request.Builder().url(url).post(body).build())
                            .execute()
                            .body())
                    .string();
            log.info("[EWX] Post response.body={}", responseJson);
            return JacksonUtil.parseObject(responseJson, clazz);
        } catch (IOException e) {
            throw new EwxException(-1, "请求网络异常");
        }
    }

    private <T> T proxyGet(String url, EwxAgent ewxAgent, Class<T> clazz) {
        try {
            String proxy = ewxAgent.getProxy();
            if (StringUtils.isNotEmpty(proxy)) {
                url = url.replace(EwxConst.EWX_BASE_URL_PLACEHOLDER, proxy);
            } else {
                url = url.replace(EwxConst.EWX_BASE_URL_PLACEHOLDER, EwxConst.EWX_BASE_DEFAULT_URL);
            }
            log.info("[EWX] Get: url={}", url);
            String responseJson = Objects.requireNonNull(okHttpClient.newCall(new Request.Builder().url(url).build())
                            .execute()
                            .body())
                    .string();
            log.info("[EWX] Get response.body={}", responseJson);
            return JacksonUtil.parseObject(responseJson, clazz);
        } catch (IOException e) {
            throw new EwxException(-1, "请求网络异常");
        }
    }


    private String getToken(String corpId, String agentId) {
        return getEwxToken(corpId, agentId).getAccessToken();
    }

    private String getToken(EwxAgent ewxAgent) {
        return getEwxToken(ewxAgent.getCorpId(), ewxAgent.getAgentId()).getAccessToken();
    }

    private String concatCacheKey(String corpId, String agentId) {
        return corpId + "---" + agentId;
    }

    @Override
    public EwxAccessToken getAccessToken(String corpId, String agentId, String corpSecret) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        return proxyGet(EwxConst.ACCESS_TOKEN_GET_URL.formatted(corpId, corpSecret), ewxAgent, EwxAccessToken.class);
    }

    @Override
    public EwxDepartmentListAttr getDepartmentList(String corpId, String agentId, Long deptId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.DEPARTMENT_LIST_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        if (Objects.nonNull(deptId)) {
            url += "&id=" + deptId;
        }
        return proxyGet(url, ewxAgent, EwxDepartmentListAttr.class);
    }

    @Override
    public EwxDepartmentListAttr getDepartmentSimpleList(String corpId, String agentId, Long deptId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.DEPARTMENT_SIMPLE_LIST_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        if (Objects.nonNull(deptId)) {
            url += "&id=" + deptId;
        }
        return proxyGet(url, ewxAgent, EwxDepartmentListAttr.class);
    }

    @Override
    public EwxDepartmentAttr getDepartment(String corpId, String agentId, Long deptId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.DEPARTMENT_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken(),deptId);
        return proxyGet(url, ewxAgent, EwxDepartmentAttr.class);
    }

    @Override
    public EwxUserDetailAttr getUserListByDeptId(String corpId, String agentId, Long deptId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.DEPARTMENT_USER_GET_URL.formatted(getEwxToken(corpId, agentId).getAccessToken(), deptId);
        return proxyGet(url, ewxAgent, EwxUserDetailAttr.class);
    }

    @Override
    public EwxExternalContactIdAttr getExternalContactList(String corpId, String agentId,  String userId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.EXTERNAL_CONTACT_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken(),userId);
        return proxyGet(url, ewxAgent, EwxExternalContactIdAttr.class);
    }

    @Override
    public EwxExternalContactDetailAttr getExternalContactDetailList(String corpId, String agentId, String[] userId, String cursor, int limit) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.EXTERNAL_CONTACT_LIST_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        EwxExternalRequest request = new EwxExternalRequest();
        request.setUserid_list(Arrays.asList(userId));
        request.setCursor(cursor);
        request.setLimit(limit);
        return proxyPost(url,ewxAgent,request,EwxExternalContactDetailAttr.class);
    }


    @Override
    public EwxCorpTagAttr getCorpTagList(String corpId, String agentId, Map<String, List<String>> data) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.CORP_TAG_LIST_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        return proxyPost(url,ewxAgent,data,EwxCorpTagAttr.class);
    }

    @Override
    public EwxEnterpriseMessageAttr createGroupMessage(String corpId, String agentId, EwxEnterpriseMessageRequest request) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.ADD_MSG_TEMPLATE_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        return proxyPost(url,ewxAgent,request,EwxEnterpriseMessageAttr.class);
    }
}
