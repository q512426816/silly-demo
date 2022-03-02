package com.iqiny.silly.demo.activitiservice.activiti.web;

import com.alibaba.fastjson.JSON;
import com.iqiny.silly.activiti.SillyActivitiTask;
import com.iqiny.silly.core.engine.SillyEngineService;
import com.iqiny.silly.core.read.MySillyMasterTask;
import com.iqiny.silly.demo.activitiservice.activiti.entity.EngineData;
import com.iqiny.silly.demo.activitiservice.activiti.entity.MySillyActivitiTask;
import com.iqiny.silly.demo.activitiservice.activiti.service.MySillyActivitiEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("all")
@RestController
@RequestMapping("/sillyeng/")
public class SillyEngineController {

    @Autowired
    private MySillyActivitiEngineService sillyEngineService;

    @PostMapping(value = "/silly/start")
    public String start(@RequestBody EngineData data) {
        return sillyEngineService.start(data.getMaster(), data.getVariableMap());
    }


    @PostMapping(value = "/silly/complete")
    public void complete(@RequestBody EngineData data) {
        sillyEngineService.complete(data.getTask(), data.getUserId(), data.getVariableMap());
    }


    @PostMapping(value = "/silly/changeTask")
    public String changeTask(@RequestBody EngineData data) {
        List<MySillyActivitiTask> taskList = sillyEngineService.changeTask(data.getTask(), data.getNodeKey(), data.getUserId());
        return JSON.toJSONString(taskList);
    }


    @PostMapping(value = "/silly/findTaskByProcessInstanceId")
    public String findTaskByProcessInstanceId(@RequestBody EngineData data) {
        List<MySillyActivitiTask> taskByProcessInstanceId = sillyEngineService.findTaskByProcessInstanceId(data.getProcessInstanceId());
        return JSON.toJSONString(taskByProcessInstanceId);
    }


    @PostMapping(value = "/silly/findTaskById")
    public MySillyActivitiTask findTaskById(@RequestBody EngineData data) {
        return sillyEngineService.findTaskById(data.getTaskId());
    }


    @PostMapping(value = "/silly/getBusinessKey")
    public String getBusinessKey(@RequestBody EngineData data) {
        return sillyEngineService.getBusinessKey(data.getProcessInstanceId());
    }


    @PostMapping(value = "/silly/getBusinessKeyByTaskId")
    public String getBusinessKeyByTaskId(@RequestBody EngineData data) {
        return sillyEngineService.getBusinessKeyByTaskId(data.getTaskId());
    }


    @PostMapping(value = "/silly/getTaskDueTime")
    public Long getTaskDueTime(@RequestBody EngineData data) {
        return sillyEngineService.getTaskDueTime(data.getTask());
    }


    @PostMapping(value = "/silly/getTaskUserIds")
    public List<String> getTaskUserIds(@RequestBody EngineData data) {
        return sillyEngineService.getTaskUserIds(data.getTask());
    }


    @PostMapping(value = "/silly/endProcessByProcessInstanceId")
    public void endProcessByProcessInstanceId(@RequestBody EngineData data) {
        sillyEngineService.endProcessByProcessInstanceId(data.getProcessInstanceId(), data.getUserId());
    }


    @PostMapping(value = "/silly/deleteProcessInstance")
    public void deleteProcessInstance(@RequestBody EngineData data) {
        sillyEngineService.deleteProcessInstance(data.getProcessInstanceId(), data.getReason());
    }


    @PostMapping(value = "/silly/getActKeyNameByProcessInstanceId")
    public String getActKeyNameByProcessInstanceId(@RequestBody EngineData data) {
        return sillyEngineService.getActKeyNameByProcessInstanceId(data.getProcessInstanceId());
    }

    @PostMapping(value = "/silly/getDoingMasterTask")
    public String getDoingMasterTask(@RequestBody EngineData data) {
        List<MySillyMasterTask> doingMasterTask = sillyEngineService.getDoingMasterTask(data.getCategory(), data.getUserId(), data.getAllGroupId());
        return JSON.toJSONString(doingMasterTask);
    }


    @PostMapping(value = "/silly/getHistoryMasterTask")
    public String getHistoryMasterTask(@RequestBody EngineData data) {
        List<MySillyMasterTask> historyMasterTask = sillyEngineService.getHistoryMasterTask(data.getCategory(), data.getUserId());
        return JSON.toJSONString(historyMasterTask);
    }


    @PostMapping(value = "/silly/getMyDoingMasterTaskId")
    public String getMyDoingMasterTaskId(@RequestBody EngineData data) {
        List<MySillyMasterTask> myDoingMasterTaskId = sillyEngineService.getMyDoingMasterTaskId(data.getCategory(), data.getUserId(), data.getMasterId(), data.getAllGroupId());
        return JSON.toJSONString(myDoingMasterTaskId);
    }


    @PostMapping(value = "/silly/findTaskByMasterId")
    public String findTaskByMasterId(@RequestBody EngineData data) {
        List<MySillyActivitiTask> taskByMasterId = sillyEngineService.findTaskByMasterId(data.getMasterId());
        return JSON.toJSONString(taskByMasterId);
    }


    @PostMapping(value = "/silly/findMyTaskByMasterId")
    public String findMyTaskByMasterId(@RequestBody EngineData data) {
        List<MySillyMasterTask> list = sillyEngineService.findMyTaskByMasterId(data.getCategory(), data.getUserId(), data.getMasterId(), data.getAllGroupId());
        return JSON.toJSONString(list);
    }


    @PostMapping(value = "/silly/changeUser")
    public void changeUser(@RequestBody EngineData data) {
        sillyEngineService.changeUser(data.getTaskId(), data.getUserId());
    }


    @PostMapping(value = "/silly/addUser")
    public void addUser(@RequestBody EngineData data) {
        sillyEngineService.addUser(data.getTaskId(), data.getUserId());
    }


    @PostMapping(value = "/silly/deleteUser")
    public void deleteUser(@RequestBody EngineData data) {
        sillyEngineService.deleteUser(data.getTaskId(), data.getUserId());
    }


    @PostMapping(value = "/silly/deleteTask")
    public void deleteTask(@RequestBody EngineData data) {
        sillyEngineService.deleteTask(data.getTaskId());
    }
}
