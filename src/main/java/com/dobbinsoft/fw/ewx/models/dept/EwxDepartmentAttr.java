package com.dobbinsoft.fw.ewx.models.dept;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EwxDepartmentAttr extends EwxBaseModel implements Serializable  {
    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    private EwxDepartment department;

}
