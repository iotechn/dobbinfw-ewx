package com.dobbinsoft.fw.ewx.events.model;

/**
 * 三种类型的通知聚合 通过ChangeType字段来确认具体消息类型
 *
 * 成员变更通知 create_user
 * * 【重要】对于2022年8月15号后通讯录助手新配置或修改的回调url，成员属性只回调UserID/Department两个字段
 *
 * 部门变更通知 change_contact
 * * 【重要】对于2022年8月15号后通讯录助手新配置或修改的回调url，部门属性只回调Id/ParentId两个字段
 *
 * 标签变更通知 change_contact
 */
public class EwxUserUpdateEvent {
}
