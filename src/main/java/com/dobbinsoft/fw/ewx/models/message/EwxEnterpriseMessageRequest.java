package com.dobbinsoft.fw.ewx.models.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 企业群发请求
 */
@Getter
@Setter
public class EwxEnterpriseMessageRequest {

    /**
     * 群发任务的类型，默认为single，表示发送给客户，group表示发送给客户群
     */
    @JsonProperty("chat_type")
    private String chatType;

    /**
     * 客户的externaluserid列表，仅在chat_type为single时有效，最多可一次指定1万个客户
     */
    @JsonProperty("external_userid")
    private List<String> externalUserId;

    /**
     * 客户群id列表，仅在chat_type为group时有效，最多可一次指定2000个客户群
     */
    @JsonProperty("chat_id_list")
    private List<String> chatIdList;

    /**
     * 要进行群发的客户标签列表，同组标签之间按或关系进行筛选，不同组标签按且关系筛选，
     * 每组最多指定100个标签，支持规则组标签
     * "tag_filter":
     * {
     * "group_list":
     * [
     * {
     * "tag_list":["ete19278asuMT123109rBAAAA","ete19MT12278109UYteaBAAAA"]
     * },
     * {
     * "tag_list":["eteIlKKHSDfuMT18Kg9rBAAAA"]
     * }
     * ]
     * },
     */
    @JsonProperty("tag_filter")
    private Map<String, List<Map<String, List<String>>>> tagFilter;

    /**
     * 发送企业群发消息的成员userid，当类型为发送给客户群时必填
     */
    @JsonProperty("sender")
    private String sender;

    /**
     * 是否允许成员在待发送客户列表中重新进行选择，默认为false，仅支持客户群发场景
     */
    @JsonProperty("allow_select")
    private Boolean allowSelect = false;

    /**
     * 消息文本内容，最多4000个字节
     */
    @JsonProperty("text")
    private Map<String, String> text;

    /**
     * 附件，最多支持添加9个附件
     * "attachments": [{
     * "msgtype": "image",
     * "image": {
     * "media_id": "MEDIA_ID",
     * "pic_url": "http://p.qpic.cn/pic_wework/3474110808/7a6344sdadfwehe42060/0"
     * }    * 	},
     * {
     * "msgtype": "link",
     * "link": {
     * "title": "消息标题",
     * "picurl": "https://example.pic.com/path",
     * "desc": "消息描述",
     * "url": "https://example.link.com/path"
     * }
     * }    ,
     * {
     * "msgtype": "miniprogram",
     * "miniprogram": {
     * "title": "消息标题",
     * "pic_media_id": "MEDIA_ID",
     * "appid": "wx8bd80126147dfAAA",
     * "page": "/path/index.html"
     * }
     * }, {
     * "msgtype": "video",
     * "video": {
     * "media_id": "MEDIA_ID"
     * }
     * }, {
     * "msgtype": "file",
     * "file": {
     * "media_id": "MEDIA_ID"
     * }
     * } ]
     */
    @JsonProperty("attachments")
    private List<Map<String, Object>> attachments;
}
