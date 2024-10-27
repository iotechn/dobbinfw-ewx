package com.dobbinsoft.fw.ewx.models.service;

import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartment;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentListAttr;

import java.util.Collections;
import java.util.List;

public class EwxService {
    private EwxClient client;

    public List<EwxDepartment> getDepartmentList(String corpId, String agentId, String corpSecret, String deptId) {
        EwxDepartmentListAttr departmentList = client.getDepartmentList(corpId, agentId, corpSecret, deptId);
        if (departmentList.getErrcode()!=0){
            return Collections.emptyList();
        }
        return departmentList.getDepartments();
    }
}
