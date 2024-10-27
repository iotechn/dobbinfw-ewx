package com.dobbinsoft.fw.ewx.events.handlers;

import com.dobbinsoft.fw.ewx.ChangeTypeConst;
import com.dobbinsoft.fw.ewx.client.EwxClient;
import com.dobbinsoft.fw.ewx.events.EwxEventType;
import com.dobbinsoft.fw.ewx.events.EwxEventsHandler;
import com.dobbinsoft.fw.ewx.events.model.EwxDeptUpdateEvent;
import com.dobbinsoft.fw.ewx.events.model.EwxTagUpdateEvent;
import com.dobbinsoft.fw.ewx.events.model.EwxUserUpdateEvent;
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
    public void handle(String cropId, String agentId, String rawMessage) {
        EwxUserUpdateEvent ewxUserUpdateEvent = JacksonXmlUtil.parseObject(rawMessage, EwxUserUpdateEvent.class);
        String ChangeType = Objects.isNull(ewxUserUpdateEvent.getChangeType()) ? null : ewxUserUpdateEvent.getChangeType();
        if (StringUtils.isNotBlank(ChangeType)) {
            if (ChangeTypeConst.CREATE_USER.equals(ChangeType)|| ChangeTypeConst.UPDATE_USER.equals(ChangeType)|| ChangeTypeConst.delete_user.equals(ChangeType)) {
                //成员变更
                // 获取UserId
                String userId = Objects.isNull(ewxUserUpdateEvent) ? null : ewxUserUpdateEvent.getUserId();
                // 接到更新回调，直接查一次最新的用户
                EwxUser user = ewxClient.getUser(cropId, agentId, userId);
                latestEwxUser(user);
            }else if (ChangeTypeConst.create_party.equals(ChangeType)|| ChangeTypeConst.update_party.equals(ChangeType)|| ChangeTypeConst.delete_party.equals(ChangeType)) {
                //部门变更
                EwxDeptUpdateEvent deptUpdateEvent = new EwxDeptUpdateEvent();
                BeanUtils.copyProperties(ewxUserUpdateEvent, deptUpdateEvent);
                latestEwxDept(deptUpdateEvent);
            }else {
                //标签变革
                EwxTagUpdateEvent ewxTagUpdateEvent = new EwxTagUpdateEvent();
                BeanUtils.copyProperties(ewxUserUpdateEvent, ewxTagUpdateEvent);
                latestTagDept(ewxTagUpdateEvent);
            }
            return;

        }
        //成员变更
        // 获取UserId
        String userId = Objects.isNull(ewxUserUpdateEvent) ? null : ewxUserUpdateEvent.getUserId();
        // 接到更新回调，直接查一次最新的用户
        EwxUser user = ewxClient.getUser(cropId, agentId, userId);
        latestEwxUser(user);
    }

    public abstract void latestEwxUser(EwxUser ewxUser);

    public abstract void latestEwxDept(EwxDeptUpdateEvent ewxDeptUpdateEvent);

    public abstract void latestTagDept(EwxTagUpdateEvent ewxTagUpdateEvent);

}
