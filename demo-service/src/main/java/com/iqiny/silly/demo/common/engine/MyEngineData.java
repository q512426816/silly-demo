package com.iqiny.silly.demo.common.engine;

import com.iqiny.silly.core.base.core.SillyMaster;
import com.iqiny.silly.core.engine.SillyTask;

import java.util.Map;
import java.util.Set;

public class MyEngineData {

    private String category;

    private String masterId;

    private String taskId;

    private String userId;

    private String processInstanceId;

    private String nodeKey;

    private String reason;

    private Set<String> allGroupId;

    private SillyMaster master;

    private SillyTask task;

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

    public SillyMaster getMaster() {
        return master;
    }

    public void setMaster(SillyMaster master) {
        this.master = master;
    }

    public SillyTask getTask() {
        return task;
    }

    public void setTask(SillyTask task) {
        this.task = task;
    }

    public Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}
