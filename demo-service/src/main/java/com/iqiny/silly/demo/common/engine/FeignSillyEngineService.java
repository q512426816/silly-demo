package com.iqiny.silly.demo.common.engine;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "SE", url = "http://127.0.0.1:8081/sillyeng/")
public interface FeignSillyEngineService {


    @PostMapping(value = "/silly/start")
    String start(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/complete")
    void complete(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/changeTask")
    String changeTask(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/findTaskByProcessInstanceId")
    String findTaskByProcessInstanceId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/findTaskById")
    String findTaskById(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getBusinessKey")
    String getBusinessKey(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getBusinessKeyByTaskId")
    String getBusinessKeyByTaskId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getTaskDueTime")
    Long getTaskDueTime(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getTaskUserIds")
    String getTaskUserIds(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/endProcessByProcessInstanceId")
    void endProcessByProcessInstanceId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/deleteProcessInstance")
    void deleteProcessInstance(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getActKeyNameByProcessInstanceId")
    String getActKeyNameByProcessInstanceId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/getDoingMasterTask")
    String getDoingMasterTask(@RequestBody MyEngineData data);


    /*@GetMapping(value = "/silly/getHistoryMasterTask")
    String getHistoryMasterTask(@RequestParam("category") String category, @RequestParam("userId") String userId);*/

    @PostMapping(value = "/silly/getMyDoingMasterTaskId")
    String getMyDoingMasterTaskId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/findTaskByMasterId")
    String findTaskByMasterId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/findMyTaskByMasterId")
    String findMyTaskByMasterId(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/changeUser")
    void changeUser(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/addUser")
    void addUser(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/deleteUser")
    void deleteUser(@RequestBody MyEngineData data);


    @PostMapping(value = "/silly/deleteTask")
    void deleteTask(@RequestBody MyEngineData data);
}
