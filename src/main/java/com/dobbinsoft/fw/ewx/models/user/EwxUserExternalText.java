package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUserExternalText implements Serializable {

    @Serial
    private static final long serialVersionUID = 1830459139630948879L;
    private String value;

}
