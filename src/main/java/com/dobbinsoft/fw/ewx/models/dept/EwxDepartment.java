package com.dobbinsoft.fw.ewx.models.dept;

import com.dobbinsoft.fw.ewx.models.EwxBaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EwxDepartment implements Serializable  {
    @Serial
    private static final long serialVersionUID = 3886834344306415181L;

    private Long id;

    private String name;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("department_leader")
    private String[] departmentLeader;

    @JsonProperty("parentid")
    private Long parentid;

    private Integer order;
}
