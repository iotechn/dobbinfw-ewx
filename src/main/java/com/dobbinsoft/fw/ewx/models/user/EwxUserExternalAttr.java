package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class EwxUserExternalAttr implements Serializable {

    @Serial
    private static final long serialVersionUID = -5226138329402328322L;
    private Integer type;
    private String name;
    private EwxUserExternalText text;
    private EwxUserExternalWeb web;
    private EwxUserExternalMiniprogram miniprogram;

}
