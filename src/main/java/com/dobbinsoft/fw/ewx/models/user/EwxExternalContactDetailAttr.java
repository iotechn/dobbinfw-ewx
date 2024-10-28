package com.dobbinsoft.fw.ewx.models.user;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxExternalContactDetailAttr extends EwxBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -1992663324207669440L;

    @JsonProperty("external_contact_list")
    private List<ExternalContact> externalContactList;
    @JsonProperty("next_cursor")
    private String nextCursor;
    @JsonProperty("fail_info")
    private FailInfo failInfo;

    @Data
    public static class ExternalContact {
        @JsonProperty("external_contact")
        private ExternalContactDetail externalContact;
        @JsonProperty("follow_info")
        private FollowInfo followInfo;

        @Data
        public static class ExternalContactDetail {
            @JsonProperty("external_userid")
            private String externalUserid;
            private String name;
            private String position;
            private String avatar;
            @JsonProperty("corp_name")
            private String corpName;
            @JsonProperty("corp_full_name")
            private String corpFullName;
            private int type;
            private int gender;
            @JsonProperty("unionid")
            private String unionId;
            @JsonProperty("external_profile")
            private ExternalProfile externalProfile;

            @Data
            public static class ExternalProfile {
                private List<ExternalAttr> external_attr;

                @Data
                public static class ExternalAttr {
                    private int type;
                    private String name;
                    private EwxUserExternalText text;
                    private EwxUserExternalWeb web;
                    private EwxUserExternalMiniprogram miniprogram;


                }
            }
        }

        @Data
        public static class FollowInfo {
            private String userid;
            private String remark;
            private String description;
            @JsonProperty("createtime")
            private long createTime;
            @JsonProperty("tag_id")
            private List<String> tagId;
            @JsonProperty("remark_corp_name")
            private String remarkCorpName;
            @JsonProperty("remark_mobiles")
            private List<String> remarkMobiles;
            @JsonProperty("oper_userid")
            private String operUserid;
            @JsonProperty("add_way")
            private int addWay;
            private EwxUserWechatChannel wechat_channels;

        }
    }

    @Data
    public static class FailInfo {
        private List<String> unlicensed_userid_list;
    }
}

