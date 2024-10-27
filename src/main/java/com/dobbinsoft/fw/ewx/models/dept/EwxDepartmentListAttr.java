package com.dobbinsoft.fw.ewx.models.dept;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EwxDepartmentListAttr extends EwxBaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    private List<EwxDepartment> departments;
}
