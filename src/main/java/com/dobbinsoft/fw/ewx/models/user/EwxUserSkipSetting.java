package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUserSkipSetting implements Serializable {
	@Serial
	private static final long serialVersionUID = -5287690093635604859L;
	private Boolean skipEmail;

}
