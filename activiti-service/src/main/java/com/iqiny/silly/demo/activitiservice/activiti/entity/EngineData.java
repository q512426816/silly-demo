package com.iqiny.silly.demo.activitiservice.activiti.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class EngineData implements Serializable {

    private String category;

    private String masterId;

    private String taskId;

    private String userId;

    private String processInstanceId;

    private String nodeKey;

    private String reason;

    private Set<String> allGroupId;

    private SillyMasterVO master;

    private MySillyActivitiTask task;

    private Map<String, Object> variableMap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Set<String> getAllGroupId() {
        return allGroupId;
    }

    public void setAllGroupId(Set<String> allGroupId) {
        this.allGroupId = allGroupId;
    }

    public SillyMasterVO getMaster() {
        return master;
    }

    public void setMaster(SillyMasterVO master) {
        this.master = master;
    }

    public MySillyActivitiTask getTask() {
        return task;
    }

    public void setTask(MySillyActivitiTask task) {
        this.task = task;
    }

    public Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}
