package com.dobbinsoft.fw.ewx.client;

import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;
import com.dobbinsoft.fw.ewx.models.archive.EwxArchiveMsgBase;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentAttr;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentListAttr;
import com.dobbinsoft.fw.ewx.models.jssdk.EwxJsSdkConfigAgentResult;
import com.dobbinsoft.fw.ewx.models.login.EwxMpLogin;
import com.dobbinsoft.fw.ewx.models.login.EwxQrLogin;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageAttr;
import com.dobbinsoft.fw.ewx.models.message.EwxEnterpriseMessageRequest;
import com.dobbinsoft.fw.ewx.models.tag.EwxCorpTagAttr;
import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;
import com.dobbinsoft.fw.ewx.models.user.*;

import java.util.List;
import java.util.Map;

public interface EwxClient {

    void addCorp(EwxCorp corp);

    void addAgent(EwxAgent agent);

    EwxCorp getCorp(String corpId);

    EwxAgent getAgent(String corpId, String agentId);

    /**
     * 获取Agent访问凭证
     * @param corpId
     * @param agentId
     * @return
     */
    public EwxAccessToken getEwxToken(String corpId, String agentId);

    /**
     * 企业微信扫码登录
     * @param corpId
     * @param agentId
     * @param code 前端扫码获得的Code
     * @return
     */
    public EwxQrLogin qrLogin(String corpId, String agentId, String code);

    /**
     * 微信小程序企业微信登录
     * @param corpId
     * @param agentId
     * @param code 前端调用qy.wx.login获得的Code
     * @return
     */
    public EwxMpLogin mpLogin(String corpId, String agentId, String code);

    /**
     * 获取用户信息
     * @param corpId
     * @param agentId
     * @param userId
     * @return
     */
    public EwxUser getUser(String corpId, String agentId, String userId);


    /**
     * 获取部门列表
     * @param corpId 企业ID
     * @param agentId
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentListAttr getDepartmentList(String corpId, String agentId, Long deptId);

    /**
     * 获取子部门ID列表
     * @param corpId 企业ID
     * @param agentId
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentListAttr getDepartmentSimpleList(String corpId, String agentId, Long deptId);

    /**
     * 获取子部门ID列表
     * @param corpId 企业ID
     * @param agentId
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentAttr getDepartment(String corpId, String agentId,  Long deptId);


    /**
     * 获取部门成员详情
     * @param corpId
     * @param agentId
     * @param deptId 部门id
     * @return
     */
    public EwxUserDetailAttr getUserListByDeptId(String corpId, String agentId, Long deptId);


    /**
     * 获取客户列表
     * @param corpId
     * @param agentId
     * @param userId 企业成员的userid
     * @return
     */
    public EwxExternalContactIdAttr getExternalContactList(String corpId, String agentId, String  userId);

    /**
     * 批量获取客户详情
     * @param corpId
     * @param agentId
     * @param userId
     * @param cursor
     * @param limit
     * @return
     */
    public EwxExternalContactDetailAttr getExternalContactDetailList(String corpId, String agentId,  String[]  userId, String cursor,int limit);


    /**
     * 获取所有标签
     * @param corpId
     * @param agentId
     * @param data
     */
    EwxCorpTagAttr getCorpTagList(String corpId, String agentId, Map<String, List<String>> data);

    /**
     * 创建企业群发消息
     * @param corpId
     * @param agentId
     * @param request 请求体
     * @return
     */
    EwxEnterpriseMessageAttr createGroupMessage(String corpId, String agentId, EwxEnterpriseMessageRequest request);


    /**
     * 获取归档消息
     * @param corpId
     * @param seq
     * @param limit
     * @param timeout
     */
    List<EwxArchiveMsgBase> getArchiveMsg(String corpId, int seq, int limit, int timeout);


    /**
     * 针对H5页面 获取其JSSDK配置
     * @param corpId
     * @param agentId
     * @param toSignUrl 待签名URL
     * @return
     */
    EwxJsSdkConfigAgentResult getJsSdkAgentConfig(String corpId, String agentId, String toSignUrl);

    /**
     * 从OAuth的Code中获取用户ID
     * @param corpId
     * @param agentId
     * @param code
     * @return
     */
    EwxOAuthResponse oauthUserInfo(String corpId, String agentId, String code);

}
