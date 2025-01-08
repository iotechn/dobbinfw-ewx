package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.ChangeTypeConst;
import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;
import com.dobbinsoft.fw.ewx.events.model.EwxTagUpdateEvent;
import com.dobbinsoft.fw.ewx.events.model.EwxUserUpdateEvent;
import com.dobbinsoft.fw.ewx.models.EwxAgent;
import com.dobbinsoft.fw.ewx.models.EwxCorp;
import com.dobbinsoft.fw.ewx.models.dept.EwxDepartmentAttr;
import com.dobbinsoft.fw.ewx.models.user.EwxUser;
import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import com.dobbinsoft.fw.support.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractUserUpdateHandler implements EwxEventsHandler {

    @Autowired
    private EwxClient ewxClient;

    @Override
    public EwxEventType type() {
        return EwxEventType.CHANGE_CONTACT_EVENT;
    }

    @Override
    public void handle(EwxAgent ewxAgent, String rawMessage) {
        EwxUserUpdateEvent ewxUserUpdateEvent = JacksonXmlUtil.parseObject(rawMessage, EwxUserUpdateEvent.class);
        if (Objects.isNull(ewxUserUpdateEvent)) {
            return;
        }
        String corpId = ewxAgent.getCorpId();
        String agentId = ewxAgent.getAgentId();
        String ChangeType = ewxUserUpdateEvent.getChangeType();
        if (StringUtils.isNotBlank(ChangeType)) {
            if (ChangeTypeConst.CREATE_USER.equals(ChangeType)|| ChangeTypeConst.UPDATE_USER.equals(ChangeType)|| ChangeTypeConst.delete_user.equals(ChangeType)) {
                //成员变更
                // 获取UserId
                String userId = ewxUserUpdateEvent.getUserId();
                // 接到更新回调，直接查一次最新的用户
                EwxUser user = ewxClient.getUser(corpId, agentId, userId);
                latestEwxUser(ewxAgent, user);
            }else if (ChangeTypeConst.create_party.equals(ChangeType)|| ChangeTypeConst.update_party.equals(ChangeType)|| ChangeTypeConst.delete_party.equals(ChangeType)) {
                //部门变更 获取部门详情
                EwxDepartmentAttr departmentAttr = ewxClient.getDepartment(corpId, agentId, ewxUserUpdateEvent.getId());
                if (Objects.nonNull(departmentAttr)){
                    departmentAttr.setChangeType(ewxUserUpdateEvent.getChangeType());
                }
                latestEwxDept(ewxAgent, departmentAttr);
            }else {
                //标签变更
                EwxTagUpdateEvent ewxTagUpdateEvent = new EwxTagUpdateEvent();
                BeanUtils.copyProperties(ewxUserUpdateEvent, ewxTagUpdateEvent);
                latestTagDept(ewxAgent, ewxTagUpdateEvent);
            }
            return;

        }
        //成员变更
        // 获取UserId
        String userId = ewxUserUpdateEvent.getUserId();
        // 接到更新回调，直接查一次最新的用户
        EwxUser user = ewxClient.getUser(corpId, agentId, userId);
        latestEwxUser(ewxAgent, user);
    }

    @Override
    public void handle(EwxCorp ewxCorp, String rawMessage) {

    }

    public abstract void latestEwxUser(EwxAgent ewxAgent, EwxUser ewxUser);

    public abstract void latestEwxDept(EwxAgent ewxAgent, EwxDepartmentAttr departmentAttr);

    public abstract void latestTagDept(EwxAgent ewxAgent, EwxTagUpdateEvent ewxTagUpdateEvent);

}
