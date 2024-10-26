package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUserAttr implements Serializable {
	@Serial
	private static final long serialVersionUID = -1992663324207669440L;
	private String name;
	private String value;

}
