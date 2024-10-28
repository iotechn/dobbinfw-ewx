package com.dobbinsoft.fw.ewx;

public class EwxConst {

    public static final String EWX_BASE_DEFAULT_URL = "https://qyapi.weixin.qq.com";

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
    public static final String EXTERNAL_CONTACT_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/list?access_token=%s&userid=%s";
    public static final String EXTERNAL_CONTACT_LIST_GET_URL = EWX_BASE_URL_PLACEHOLDER + "/cgi-bin/externalcontact/batch/get_by_user?access_token=%s";

}
