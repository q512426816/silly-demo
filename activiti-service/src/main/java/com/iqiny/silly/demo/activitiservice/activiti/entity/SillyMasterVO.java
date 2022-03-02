package com.iqiny.silly.demo.activitiservice.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iqiny.silly.core.base.core.SillyMaster;

import java.util.Date;

public class SillyMasterVO implements SillyMaster {

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

    @Override
    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    @Override
    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }

    @Override
    public String getProcessId() {
        return processId;
    }

    @Override
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStartUserId() {
        return startUserId;
    }

    @Override
    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getCloseUserId() {
        return closeUserId;
    }

    @Override
    public void setCloseUserId(String closeUserId) {
        this.closeUserId = closeUserId;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCloseDate() {
        return closeDate;
    }

    @Override
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getHandleUserName() {
        return handleUserName;
    }

    @Override
    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    @Override
    public String processKey() {
        return processKey;
    }

    @Override
    public String processVersion() {
        return processVersion;
    }

    @Override
    public String startStatus() {
        // 请填写开始状态值
        return "10";
    }

    @Override
    public String doingStatus() {
        // 请填写进行中状态值
        return "20";
    }

    @Override
    public String endStatus() {
        // 请填写流程结束状态值
        return "90";
    }

    @Override
    public String usedCategory() {
        return category;
    }
}
