package com.dobbinsoft.fw.ewx.client;

import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentAttr;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentListAttr;
import com.dobbinsoft.fw.ewx.models.event.EwxEncryptMessageRequest;
import com.dobbinsoft.fw.ewx.models.event.EwxUrlVerifyRequest;
import com.dobbinsoft.fw.ewx.models.login.EwxMpLogin;
import com.dobbinsoft.fw.ewx.models.login.EwxQrLogin;
import com.dobbinsoft.fw.ewx.models.token.EwxAccessToken;
import com.dobbinsoft.fw.ewx.models.user.EwxUser;
import netscape.javascript.JSObject;

public interface EwxClient {

    void addAgent(EwxAgent agent);

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
     * 校验回调URL
     * @param corpId
     * @param agentId
     * @param request
     * @return
     */
    public String verifyNotifyUrl(String corpId, String agentId, EwxUrlVerifyRequest request);

    public String routeEvent(String corpId, String agentId, EwxUrlVerifyRequest request, EwxEncryptMessageRequest encryptMessageRequest);

    /**
     * 获取access_token
     * @param corpId 企业ID
     * @param corpSecret 应用的凭证密钥
     * @return
     */
    public EwxAccessToken getAccessToken(String corpId, String agentId, String corpSecret);

    /**
     * 获取部门列表
     * @param corpId 企业ID
     * @param agentId
     * @param corpSecret 应用的凭证密钥
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentListAttr getDepartmentList(String corpId, String agentId, String corpSecret, String deptId);

    /**
     * 获取子部门ID列表
     * @param corpId 企业ID
     * @param agentId
     * @param corpSecret 应用的凭证密钥
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentListAttr getDepartmentSimpleList(String corpId, String agentId, String corpSecret,String deptId);

    /**
     * 获取子部门ID列表
     * @param corpId 企业ID
     * @param agentId
     * @param corpSecret 应用的凭证密钥
     * @param deptId 部门id。获取指定部门及其下的子部门 如果不填，默认获取全量组织架构
     * @return
     */
    public EwxDepartmentAttr getDepartment(String corpId, String agentId, String corpSecret, String deptId);
}
