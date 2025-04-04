package com.dobbinsoft.fw.ewx.client;

import com.dobbinsoft.fw.ewx.EwxConst;
import com.dobbinsoft.fw.ewx.cache.EwxCache;
import com.dobbinsoft.fw.ewx.enums.EwxJsTicketEnum;
import com.dobbinsoft.fw.ewx.exception.EwxException;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;
import com.dobbinsoft.fw.ewx.models.archive.*;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentAttr;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentListAttr;
import com.dobbinsoft.fw.ewx.models.external.EwxExternalContactDetailAttr;
import com.dobbinsoft.fw.ewx.models.external.EwxExternalContactDetailListAttr;
import com.dobbinsoft.fw.ewx.models.external.EwxExternalContactIdAttr;
import com.dobbinsoft.fw.ewx.models.external.EwxExternalRequest;
import com.dobbinsoft.fw.ewx.models.jssdk.EwxJsSdkApiTicket;
import com.dobbinsoft.fw.ewx.models.jssdk.EwxJsSdkConfigAgentResult;
import com.dobbinsoft.fw.ewx.models.login.EwxMpLogin;
import com.dobbinsoft.fw.ewx.models.login.EwxQrLogin;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageAttr;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageRequest;
import com.dobbinsoft.fw.ewx.models.tag.EwxCorpTagAttr;
import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;
import com.dobbinsoft.fw.ewx.models.user.*;
import com.dobbinsoft.fw.ewx.utils.RSAEncrypt;
import com.dobbinsoft.fw.support.utils.CollectionUtils;
import com.dobbinsoft.fw.support.utils.DigestUtils;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.StringUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tencent.wework.Finance;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EwxClientImpl implements EwxClient {

    @Autowired
    private EwxCache ewxCache;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    // Key corpId + "---" + agentId
    private final Map<String, EwxAgent> agentMap = new ConcurrentHashMap<>();
    // Key corpId
    private final Map<String, EwxCorp> corpMap = new ConcurrentHashMap<>();
    // Key corpId + "---" + archiveSecret, value: sdk id
    private final Map<String, Long> archiveSdkMap = new ConcurrentHashMap<>();

    // Key corpId + "---" + agentId 7200秒
    private final Cache<String, String> jsSdkTicketCache =
            Caffeine.newBuilder().expireAfterWrite(6200, TimeUnit.SECONDS).build();


    @Override
    public void addCorp(EwxCorp corp) {
        corpMap.put(corp.getCorpId(), corp);
    }

    @Override
    public void addAgent(EwxAgent agent) {
        agentMap.put(agent.cacheKey(), agent);
    }

    @Override
    public EwxCorp getCorp(String corpId) {
        return corpMap.get(corpId);
    }

    @Override
    public EwxAgent getAgent(String corpId, String agentId) {
        return agentMap.get(concatCacheKey(corpId, agentId));
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
    public EwxExternalContactDetailAttr getExternalContact(String corpId, String agentId, String externalContactId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.EXTERNAL_CONTACT_GET_URL.formatted(getEwxToken(corpId, agentId).getAccessToken(), externalContactId);
        return proxyGet(url, ewxAgent, EwxExternalContactDetailAttr.class);
    }

    @Override
    public EwxExternalContactIdAttr getExternalContactList(String corpId, String agentId, String userId) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.EXTERNAL_CONTACT_LIST_GET_URL.formatted(getEwxToken(corpId,agentId).getAccessToken(),userId);
        return proxyGet(url, ewxAgent, EwxExternalContactIdAttr.class);
    }

    @Override
    public EwxExternalContactDetailListAttr getExternalContactDetailList(String corpId, String agentId, String[] userId, String cursor, int limit) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url=EwxConst.EXTERNAL_CONTACT_LIST_GET_BY_USER_URL.formatted(getEwxToken(corpId,agentId).getAccessToken());
        EwxExternalRequest request = new EwxExternalRequest();
        request.setUserid_list(Arrays.asList(userId));
        request.setCursor(cursor);
        request.setLimit(limit);
        return proxyPost(url,ewxAgent,request, EwxExternalContactDetailListAttr.class);
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


    @Override
    public List<EwxArchiveMsgBase> getArchiveMsg(String corpId, int seq, int limit, int timeout) {
        EwxCorp ewxCorp = corpMap.get(corpId);
        if (StringUtils.isEmpty(ewxCorp.getArchiveSecret())) {
            log.info("[EWX] 会话归档拉取 未配置SDK Secret");
            return Collections.emptyList();
        }
        Long archiveSdk = archiveSdkMap.get(ewxCorp.archiveSdkKey());
        if (archiveSdk == null) {
            synchronized (this) {
                // DCL 防止重复初始化Sdk
                Long tmp = archiveSdkMap.get(ewxCorp.archiveSdkKey());
                if (tmp == null) {
                    archiveSdk = Finance.NewSdk();
                    Finance.Init(archiveSdk, ewxCorp.getCorpId(), ewxCorp.getArchiveSecret());
                    archiveSdkMap.put(ewxCorp.archiveSdkKey(), archiveSdk);
                } else {
                    archiveSdk = tmp;
                }
            }
        }

        //每次使用GetChatData拉取存档前需要调用NewSlice获取一个slice，在使用完slice中数据后，还需要调用FreeSlice释放。
        long slice = Finance.NewSlice();
        try {
            int ret = Finance.GetChatData(archiveSdk, seq, limit, "", "", timeout, slice);
            if (ret != 0) {
                log.info("[EWX] 会话归档拉取 解析slice失败 slice={}", slice);
                return Collections.emptyList();
            }
            String rawData = Finance.GetContentFromSlice(slice);
            EwxArchiveRawData ewxArchiveRawData = JacksonUtil.parseObject(rawData, EwxArchiveRawData.class);
            if (ewxArchiveRawData.getErrcode() != 0) {
                log.info("[EWX] 会话归档拉取 error response={}", rawData);
                return Collections.emptyList();
            }
            List<EwxArchiveRawData.ChatdataDTO> chatDataList = ewxArchiveRawData.getChatdata();
            if (CollectionUtils.isEmpty(chatDataList)) {
                return Collections.emptyList();
            }
            List<EwxArchiveMsgBase> result = new ArrayList<>();
            int failCount = 0;
            for (EwxArchiveRawData.ChatdataDTO chatData : chatDataList) {
                String encryptRandomKey = chatData.getEncryptRandomKey();
                String encryptChatMsg = chatData.getEncryptChatMsg();
                long msg = Finance.NewSlice();

                try {
                    String decryptRandomKey = RSAEncrypt.decryptRSA(encryptRandomKey, ewxCorp.getArchivePrivateKey());
                    if (StringUtils.isEmpty(decryptRandomKey)) {
                        log.error("[EWX] 会话归档拉取 单条解密失败 encryptRandomKey:{}", encryptRandomKey);
                        failCount++;
                        continue;
                    }
                    ret = Finance.DecryptData(archiveSdk, decryptRandomKey, encryptChatMsg, msg);
                    if (ret != 0) {
                        log.error("[EWX] 会话归档拉取 单条error ret:{}", ret);
                        failCount++;
                        continue;
                    }
                    String plaintext = Finance.GetContentFromSlice(msg);
                    log.info("[EWX] 会话归档拉取 plaintext={}", plaintext);
                    EwxArchiveMsgBase ewxArchiveMsgBase = JacksonUtil.parseObject(plaintext, EwxArchiveMsgBase.class);
                    switch (ewxArchiveMsgBase.getMsgtype()) {
                        case "text" -> {
                            EwxArchiveMsgText ewxArchiveMsgText = JacksonUtil.parseObject(plaintext, EwxArchiveMsgText.class);
                            ewxArchiveMsgText.setSeq(chatData.getSeq());
                            result.add(ewxArchiveMsgText);
                        }
                        case "image" -> {
                            EwxArchiveMsgImage ewxArchiveMsgImage = JacksonUtil.parseObject(plaintext, EwxArchiveMsgImage.class);
                            ewxArchiveMsgImage.setSeq(chatData.getSeq());
                            result.add(ewxArchiveMsgImage);
                        }
                        case "voice" -> {
                            EwxArchiveMsgVoice ewxArchiveMsgVoice = JacksonUtil.parseObject(plaintext, EwxArchiveMsgVoice.class);
                            ewxArchiveMsgVoice.setSeq(chatData.getSeq());
                            result.add(ewxArchiveMsgVoice);
                        }
                        case "video" -> {
                            EwxArchiveMsgVideo ewxArchiveMsgVideo = JacksonUtil.parseObject(plaintext, EwxArchiveMsgVideo.class);
                            ewxArchiveMsgVideo.setSeq(chatData.getSeq());
                            result.add(ewxArchiveMsgVideo);
                        }
                        case "location" -> {
                            EwxArchiveMsgLocation ewxArchiveMsgLocation = JacksonUtil.parseObject(plaintext, EwxArchiveMsgLocation.class);
                            ewxArchiveMsgLocation.setSeq(chatData.getSeq());
                            result.add(ewxArchiveMsgLocation);
                        }
                        case null, default ->
                                log.info("[EWX] 会话归档拉取 不支持的消息类型 MsgType={}", ewxArchiveMsgBase.getMsgtype());
                    }
                } finally {
                    Finance.FreeSlice(msg);
                }
            }
            log.info("[EWX] 会话归档拉取 success={}条, fail={}条", result.size(), failCount);
            return result;
        } finally {
            // 保证NewSlice成对
            Finance.FreeSlice(slice);
        }
    }

    @Override
    public EwxJsSdkConfigAgentResult getJsSdkConfig(String corpId, String agentId, String toSignUrl, EwxJsTicketEnum ticketEnum) {
        String key = concatCacheKey(corpId, agentId);
        EwxAgent ewxAgent = agentMap.get(key);
        String cacheKey = key + "_" + ticketEnum.getCode();
        String url = (ticketEnum == EwxJsTicketEnum.CORP ? EwxConst.GET_JSAPI_TICKET : EwxConst.GET_AGENT_JSAPI_TICKET)
                .formatted(getEwxToken(corpId,agentId).getAccessToken());
        String ticket = jsSdkTicketCache.get(cacheKey, k -> {
            EwxJsSdkApiTicket ewxJsSdkApiTicket = proxyGet(url, ewxAgent, EwxJsSdkApiTicket.class);
            return ewxJsSdkApiTicket.getTicket();
        });
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String nonceStr = StringUtils.uuid();

        String toSignStr = (
            "jsapi_ticket=%s" +
            "&noncestr=%s" +
            "&timestamp=%s" +
            "&url=%s"
        ).formatted(ticket, nonceStr, timestamp, toSignUrl);
        String sign = DigestUtils.sha1Hex(toSignStr);
        EwxJsSdkConfigAgentResult agentResult = new EwxJsSdkConfigAgentResult();
        agentResult.setCorpId(corpId);
        agentResult.setAgentId(agentId);
        agentResult.setTimestamp(timestamp);
        agentResult.setNonceStr(nonceStr);
        agentResult.setSignature(sign);
        return agentResult;
    }

    @Override
    public String getAgentOauthUrl(String corpId, String agentId, String redirectURI, String state, String scope) {
        return EwxConst.OAUTH_URL.formatted(
                corpId,
                URLEncoder.encode(redirectURI, StandardCharsets.UTF_8),
                scope,
                state,
                String.valueOf(agentId));
    }

    @Override
    public EwxOAuthResponse oauthUserInfo(String corpId, String agentId, String code) {
        EwxAgent ewxAgent = agentMap.get(concatCacheKey(corpId, agentId));
        String url = EwxConst.OAUTH_CODE_TO_USER_ID.formatted(getEwxToken(corpId,agentId).getAccessToken(), code);
        return proxyGet(url, ewxAgent, EwxOAuthResponse.class);
    }
}
