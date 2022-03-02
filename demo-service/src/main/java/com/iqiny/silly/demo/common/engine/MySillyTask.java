package com.iqiny.silly.demo.common.engine;

import com.iqiny.silly.core.engine.SillyTask;

public class MySillyTask implements SillyTask {

    private String id;
    private String executionId;
    private String processInstanceId;
    private String name;
    private String taskDefinitionKey;
    private String assignee;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    @Override
    public String getAssignee() {
        return assignee;
    }

    @Override
    public Object getTask() {
        return null;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

}
