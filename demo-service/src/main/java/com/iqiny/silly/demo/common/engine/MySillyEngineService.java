package com.iqiny.silly.demo.common.engine;

import com.alibaba.fastjson.JSONObject;
import com.iqiny.silly.core.base.core.SillyMaster;
import com.iqiny.silly.core.config.SillyCategoryConfig;
import com.iqiny.silly.core.config.SillyConfigUtil;
import com.iqiny.silly.core.engine.SillyEngineService;
import com.iqiny.silly.core.engine.SillyTask;
import com.iqiny.silly.core.read.MySillyMasterTask;
import com.iqiny.silly.demo.common.resume.service.MySillyResumeService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MySillyEngineService implements SillyEngineService<MySillyTask, MySillyMasterTask> {

    private String category;

    private FeignSillyEngineService feignSillyEngineService;
    private MySillyResumeService sillyResumeService;

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void init() {
        if (category != null) {
            SillyCategoryConfig sillyConfig = SillyConfigUtil.getSillyConfig(category);
            // 可以根据不同业务获取不同的 引擎服务
            feignSillyEngineService = sillyConfig.getSillyContext().getBean(FeignSillyEngineService.class);
            sillyResumeService = sillyConfig.getSillyContext().getBean(MySillyResumeService.class);
        }
    }

    @Override
    public String start(SillyMaster master, Map<String, Object> variableMap) {
        MyEngineData data = new MyEngineData();
        data.setMaster(master);
        data.setVariableMap(variableMap);
        return feignSillyEngineService.start(data);
    }

    @Override
    public void complete(SillyTask task, String userId, Map<String, Object> variableMap) {
        MyEngineData data = new MyEngineData();
        data.setTask(task);
        data.setUserId(userId);
        data.setVariableMap(variableMap);
        feignSillyEngineService.complete(data);
    }


    @Override
    public List<MySillyTask> changeTask(SillyTask task, String nodeKey, String userId) {
        MyEngineData data = new MyEngineData();
        data.setTask(task);
        data.setUserId(userId);
        data.setNodeKey(nodeKey);
        return JSONObject.parseArray(feignSillyEngineService.changeTask(data), MySillyTask.class);
    }

    @Override
    public List<MySillyTask> findTaskByProcessInstanceId(String processInstanceId) {
        MyEngineData data = new MyEngineData();
        data.setProcessInstanceId(processInstanceId);
        return JSONObject.parseArray(feignSillyEngineService.findTaskByProcessInstanceId(data), MySillyTask.class);
    }

    @Override
    public MySillyTask findTaskById(String taskId) {
        MyEngineData data = new MyEngineData();
        data.setTaskId(taskId);
        return JSONObject.parseObject(feignSillyEngineService.findTaskById(data), MySillyTask.class);
    }

    @Override
    public String getBusinessKey(String processInstanceId) {
        MyEngineData data = new MyEngineData();
        data.setProcessInstanceId(processInstanceId);
        return feignSillyEngineService.getBusinessKey(data);
    }

    @Override
    public String getBusinessKeyByTaskId(String taskId) {
        MyEngineData data = new MyEngineData();
        data.setTaskId(taskId);
        return feignSillyEngineService.getBusinessKeyByTaskId(data);
    }

    @Override
    public Long getTaskDueTime(SillyTask task) {
        MyEngineData data = new MyEngineData();
        data.setTask(task);
        return feignSillyEngineService.getTaskDueTime(data);
    }

    @Override
    public List<String> getTaskUserIds(SillyTask task) {
        MyEngineData data = new MyEngineData();
        data.setTask(task);
        return JSONObject.parseArray(feignSillyEngineService.getTaskUserIds(data), String.class);
    }

    @Override
    public void endProcessByProcessInstanceId(String processInstanceId, String userId) {
        MyEngineData data = new MyEngineData();
        data.setProcessInstanceId(processInstanceId);
        data.setUserId(userId);
        feignSillyEngineService.endProcessByProcessInstanceId(data);
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        MyEngineData data = new MyEngineData();
        data.setProcessInstanceId(processInstanceId);
        data.setReason(deleteReason);
        feignSillyEngineService.deleteProcessInstance(data);
    }

    @Override
    public String getActKeyNameByProcessInstanceId(String processInstanceId) {
        MyEngineData data = new MyEngineData();
        data.setProcessInstanceId(processInstanceId);
        return feignSillyEngineService.getActKeyNameByProcessInstanceId(data);
    }

    @Override
    public List<MySillyMasterTask> getDoingMasterTask(String category, String userId, Set<String> allGroupId) {
        MyEngineData data = new MyEngineData();
        data.setCategory(category);
        data.setUserId(userId);
        data.setAllGroupId(allGroupId);
        return JSONObject.parseArray(feignSillyEngineService.getDoingMasterTask(data), MySillyMasterTask.class);
    }

    @Override
    public List<MySillyMasterTask> getHistoryMasterTask(String category, String userId) {
        return sillyResumeService.getHistoryMasterTask(category, userId);
    }

    @Override
    public List<MySillyMasterTask> getMyDoingMasterTaskId(String category, String userId, String masterId, Set<String> allGroupId) {
        MyEngineData data = new MyEngineData();
        data.setCategory(category);
        data.setUserId(userId);
        data.setMasterId(masterId);
        data.setAllGroupId(allGroupId);
        return JSONObject.parseArray(feignSillyEngineService.getMyDoingMasterTaskId(data), MySillyMasterTask.class);
    }

    @Override
    public List<MySillyTask> findTaskByMasterId(String masterId) {
        MyEngineData data = new MyEngineData();
        data.setMasterId(masterId);
        return JSONObject.parseArray(feignSillyEngineService.findTaskByMasterId(data), MySillyTask.class);
    }

    @Override
    public List<MySillyMasterTask> findMyTaskByMasterId(String category, String userId, String masterId, Set<String> allGroupId) {
        MyEngineData data = new MyEngineData();
        data.setCategory(category);
        data.setUserId(userId);
        data.setMasterId(masterId);
        data.setAllGroupId(allGroupId);
        return JSONObject.parseArray(feignSillyEngineService.findMyTaskByMasterId(data), MySillyMasterTask.class);
    }

    @Override
    public void changeUser(String taskId, String userId) {
        MyEngineData data = new MyEngineData();
        data.setUserId(userId);
        data.setTaskId(taskId);
        feignSillyEngineService.changeUser(data);
    }

    @Override
    public void addUser(String taskId, String userId) {
        MyEngineData data = new MyEngineData();
        data.setUserId(userId);
        data.setTaskId(taskId);
        feignSillyEngineService.addUser(data);
    }

    @Override
    public void deleteUser(String taskId, String userId) {
        MyEngineData data = new MyEngineData();
        data.setUserId(userId);
        data.setTaskId(taskId);
        feignSillyEngineService.deleteUser(data);
    }

    @Override
    public void deleteTask(String taskId) {
        MyEngineData data = new MyEngineData();
        data.setTaskId(taskId);
        feignSillyEngineService.deleteTask(data);
    }

}
