package com.dobbinsoft.fw.ewx.models.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Setter
@Getter
public class EwxUserExternalWeb implements Serializable {

    @Serial
    private static final long serialVersionUID = 3996967071055627309L;
    private String url;
    private String title;

}
