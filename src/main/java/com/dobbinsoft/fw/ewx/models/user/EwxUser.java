package com.dobbinsoft.fw.ewx.models.user;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUser extends EwxBaseModel implements Serializable {
	@Serial
	private static final long serialVersionUID = 3886834344306415181L;

	private String userid;
	private String name;
	private int[] department;
	private int[] order;
	private String position;
	private String mobile;
	private String gender;
	private String email;
	@JsonProperty("is_leader_in_dept")
	private int[] isLeaderInDept;
	@JsonProperty("avatar_mediaid")
	private String avatarMediaid;
	private String alias;
	private Integer status;
	private Integer enable;
	@JsonProperty("english_name")
	private String englishName;
	private String avatar;
	@JsonProperty("thumb_avatar")
	private String thumbAvatar;
	private String telephone;
	private String address;
	@JsonProperty("hide_mobile")
	private Integer hideMobile;
	@JsonProperty("open_userid")
	private String openUserid;
	@JsonProperty("main_department")
	private Integer mainDepartment;
	@JsonProperty("qr_code")
	private String qrCode;
	@JsonProperty("external_position")
	private String externalPosition;
	@JsonProperty("external_profile")
	private EwxUserExternalProfile externalProfile;
	private EwxUserExtraAttrs extattr;
	private String ChangeType;

}
