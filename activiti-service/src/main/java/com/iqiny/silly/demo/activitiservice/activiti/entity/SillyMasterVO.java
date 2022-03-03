package com.iqiny.silly.demo.activitiservice.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SillyMasterVO {

    protected String id;
    protected String category;
    protected String processKey;
    protected String processVersion;
    protected String processId;
    protected String status;
    protected String startUserId;
    protected Date startDate;
    protected String closeUserId;
    protected Date closeDate;
    protected String taskName;
    protected String handleUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createDate;
    /**
     * 创建人
     */
    protected String createUserId;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateDate;
    /**
     * 更新人
     */
    protected String updateUserId;

    /**
     * 更新人
     */
    protected String updateUserName;
    /**
     * 创建人
     */
    protected String createUserName;

    public String getProcessKey() {
        return processKey;
    }

    
    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessVersion() {
        return processVersion;
    }


    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }


    public String getProcessId() {
        return processId;
    }


    public void setProcessId(String processId) {
        this.processId = processId;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getStartUserId() {
        return startUserId;
    }


    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public String getCloseUserId() {
        return closeUserId;
    }


    public void setCloseUserId(String closeUserId) {
        this.closeUserId = closeUserId;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCloseDate() {
        return closeDate;
    }


    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }


    public String getTaskName() {
        return taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getHandleUserName() {
        return handleUserName;
    }


    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    public String processKey() {
        return processKey;
    }


    public String processVersion() {
        return processVersion;
    }


    public String startStatus() {
        // 请填写开始状态值
        return "10";
    }


    public String doingStatus() {
        // 请填写进行中状态值
        return "20";
    }


    public String endStatus() {
        // 请填写流程结束状态值
        return "90";
    }


    public String usedCategory() {
        return category;
    }
}
