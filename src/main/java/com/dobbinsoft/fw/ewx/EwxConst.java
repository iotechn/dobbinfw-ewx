package com.dobbinsoft.fw.ewx;

public class EwxConst {

    public static final String EWX_BASE_DEFAULT_URL = "https://qyapi.weixin.qq.com";

    public static final String EWX_OPEN_PLATFORM_DEFAULT_URL = "https://open.weixin.qq.com";

    public static final String EWX_BASE_URL_PLACEHOLDER = "{{EWX_BASE_URL_PLACEHOLDER}}";

    public static final String EWX_ACCESS_TOKEN_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    /***************** 企业微信用户相关URL *******************/
    public static final String USER_CREATE_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/create?access_token=%s";
    public static final String USER_UPDATE_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/update?access_token=%s";
    public static final String USER_DELETE_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/delete?access_token=%s&userid=%s";
    public static final String USER_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/get?access_token=%s&userid=%s";
    public static final String USER_SIMPLE_LIST_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/simplelist?access_token=%s&department_id=%s&fetch_child=&3&status=&4";
    public static final String USER_LIST_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=&3&status=&4";
    public static final String USER_GET_USER_DETAIL_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/getuserdetail?access_token=%s";
    public static final String USER_GET_USER_BY_EMAIL_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/get_userid_by_email?access_token=%s";
    public static final String USER_QR_LOGIN_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
    public static final String JS_CODE_TO_SESSION = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/miniprogram/jscode2session?access_token=%s&js_code=%s&grant_type=authorization_code";
    public static final String ACCESS_TOKEN_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    public static final String DEPARTMENT_LIST_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/department/list?access_token=%s";
    public static final String DEPARTMENT_SIMPLE_LIST_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/department/simplelist?access_token=%s";
    public static final String DEPARTMENT_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/department/get?access_token=%s&id=%s";
    public static final String DEPARTMENT_USER_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/user/list?access_token=%s&department_id=%s";
    public static final String EXTERNAL_CONTACT_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/get?access_token=%s&external_userid=%s";
    public static final String EXTERNAL_CONTACT_LIST_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/list?access_token=%s&userid=%s";
    public static final String EXTERNAL_CONTACT_LIST_GET_BY_USER_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/batch/get_by_user?access_token=%s";
    public static final String CORP_TAG_LIST_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/get_corp_tag_list?access_token=%s";
    public static final String ADD_MSG_TEMPLATE_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/add_msg_template?access_token=%s";
    // JSAPI_TICKET 和 AGENT_JSAPI_TICKET 区别参考 https://developer.work.weixin.qq.com/document/path/96909
    public static final String GET_JSAPI_TICKET = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/get_jsapi_ticket?access_token=%s";
    public static final String GET_AGENT_JSAPI_TICKET = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/ticket/get?access_token=%s&type=agent_config";
    public static final String OAUTH_CODE_TO_USER_ID = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/auth/getuserinfo?access_token=%s&code=%s";
    public static final String OAUTH_URL = EWX_OPEN_PLATFORM_DEFAULT_URL + "/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&agentid=%s#wechat_redirect";
}
