package com.dobbinsoft.fw.ewx.models.external;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.dobbinsoft.fw.ewx.models.user.EwxUserExternalMiniprogram;
import com.dobbinsoft.fw.ewx.models.user.EwxUserExternalText;
import com.dobbinsoft.fw.ewx.models.user.EwxUserExternalWeb;
import com.dobbinsoft.fw.ewx.models.user.EwxUserWechatChannel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxExternalContactDetailAttr extends EwxBaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -1992663324207669440L;


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
        private Integer gender;
        @JsonProperty("unionid")
        private String unionId;
        @JsonProperty("external_profile")
        private EwxExternalContactDetailListAttr.ExternalContact.ExternalContactDetail.ExternalProfile externalProfile;

        @Data
        public static class ExternalProfile {
            private List<EwxExternalContactDetailListAttr.ExternalContact.ExternalContactDetail.ExternalProfile.ExternalAttr> external_attr;

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
