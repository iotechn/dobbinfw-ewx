package com.dobbinsoft.fw.ewx.models.user;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EwxUserDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = -1992663324207669440L;

    @JsonProperty("userid")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("department")
    private Integer[] department;

    @JsonProperty("order")
    private Integer[] order;

    @JsonProperty("position")
    private String position;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("email")
    private String email;

    @JsonProperty("biz_mail")
    private String bizMail;

    @JsonProperty("is_leader_in_dept")
    private Integer[] isLeaderInDept;

    @JsonProperty("direct_leader")
    private String[] directLeader;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("thumb_avatar")
    private String thumbAvatar;

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("address")
    private String address;

    @JsonProperty("english_name")
    private String englishName;

    @JsonProperty("open_userid")
    private String openUserId;

    @JsonProperty("main_department")
    private Integer mainDepartment;

    private EwxUserExtraAttrs extattr;

    @JsonProperty("qr_code")
    private String qrCode;
    @JsonProperty("external_position")
    private String externalPosition;

    @JsonProperty("external_profile")
    private EwxUserExternalProfile externalProfile;

    @JsonProperty("external_attr")
    private EwxUserExternalAttr externalAttr;

}
