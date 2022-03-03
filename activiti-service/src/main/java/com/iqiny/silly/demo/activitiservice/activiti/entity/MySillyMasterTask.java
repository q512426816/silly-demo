/*
 *  Copyright  iqiny.com
 *
 *  https://gitee.com/iqiny/silly
 *
 *  project name：silly-core
 *  project description：top silly project pom.xml file
 */
package com.iqiny.silly.demo.activitiservice.activiti.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySillyMasterTask implements Serializable {

    /**
     * 业务分类
     */
    private String category;

    /**
     * 业务主表ID
     */
    private String masterId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务节点ID
     */
    private String nodeKey;

    /**
     * 任务类型 assignee / user / group
     */
    private String taskType;

    /**
     * 其他参数
     */
    private Map<String, Object> params = new LinkedHashMap<>();

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

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
