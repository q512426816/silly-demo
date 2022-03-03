/*
 *  Copyright  iqiny.com
 *
 *  https://gitee.com/iqiny/silly
 *
 *  project name：silly-activiti
 *  project description：top silly project pom.xml file
 */
package com.iqiny.silly.demo.activitiservice.activiti.entity;

import org.activiti.engine.task.Task;

import java.io.Serializable;

public class MySillyActivitiTask implements Serializable {

    private String id;
    private String executionId;
    private String processInstanceId;
    private String name;
    private String taskDefinitionKey;
    private String assignee;

    public MySillyActivitiTask(){}

    public MySillyActivitiTask(Task task) {

        this.id = task.getId();
        this.executionId = task.getExecutionId();
        this.processInstanceId = task.getProcessInstanceId();
        this.name = task.getName();
        this.taskDefinitionKey = task.getTaskDefinitionKey();
        this.assignee = task.getAssignee();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }


    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }


    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }


    public Object getTask() {
        return null;
    }

}
