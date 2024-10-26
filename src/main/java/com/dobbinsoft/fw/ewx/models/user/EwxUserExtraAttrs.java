package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class EwxUserExtraAttrs implements Serializable {
	@Serial
	private static final long serialVersionUID = 1814674216048970710L;
	private List<EwxUserAttr> attrs;

}
