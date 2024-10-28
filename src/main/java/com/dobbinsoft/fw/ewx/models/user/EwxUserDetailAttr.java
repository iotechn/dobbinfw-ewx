package com.dobbinsoft.fw.ewx.models.user;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class EwxUserDetailAttr extends EwxBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    private List<EwxUserDetail> userlist;
}
