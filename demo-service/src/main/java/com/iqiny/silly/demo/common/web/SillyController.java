package com.iqiny.silly.demo.common.web;

import com.iqiny.silly.common.util.SillyAssert;
import com.iqiny.silly.core.base.core.SillyMaster;
import com.iqiny.silly.core.config.SillyCategoryConfig;
import com.iqiny.silly.core.config.SillyConfigUtil;
import com.iqiny.silly.core.config.property.option.SillyProcessNodeOptionProperty;
import com.iqiny.silly.core.service.SillyReadService;
import com.iqiny.silly.demo.common.sc.MyAssert;
import com.iqiny.silly.demo.common.sc.SillyR;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/silly/{category}/")
public class SillyController {

    @ModelAttribute
    public SillyCategoryConfig getConfig(@PathVariable("category") String category) {
        MyAssert.notEmpty(category, "业务分类信息不可为空");
        return SillyConfigUtil.getSillyConfig(category);
    }

    /**
     * 待办任务
     *
     * @param categoryConfig
     * @param params
     * @return
     */
    @GetMapping("/todo")
    public SillyR todo(SillyCategoryConfig categoryConfig, @RequestParam Map<String, Object> params) {
        Object o = categoryConfig.getSillyReadService().queryDoingPage(params);
        return SillyR.ok(o);
    }

    /**
     * 历史处置任务
     *
     * @param categoryConfig
     * @param params
     * @return
     */
    @GetMapping("/history")
    public SillyR history(SillyCategoryConfig categoryConfig, @RequestParam Map<String, Object> params) {
        Object o = categoryConfig.getSillyReadService().queryHistoryPage(params);
        return SillyR.ok(o);
    }

    /**
     * 查询列表
     *
     * @param categoryConfig
     * @param params
     * @return
     */
    @GetMapping("/query")
    public SillyR query(SillyCategoryConfig categoryConfig, @RequestParam Map<String, Object> params) {
        Object o = categoryConfig.getSillyReadService().queryPage(params);
        return SillyR.ok(o);
    }

    /**
     * 新增数据
     *
     * @param categoryConfig
     * @param params
     * @return
     */
    @PostMapping("/add")
    public SillyR add(SillyCategoryConfig categoryConfig, @RequestBody Map<String, Object> params) {
        Object o = categoryConfig.getSillyWriteService().saveOrNewMap(params);
        return SillyR.ok(o);
    }

    /**
     * 删除数据
     *
     * @param categoryConfig
     * @param masterId
     * @return
     */
    @PostMapping("/delete/{masterId}")
    public SillyR delete(SillyCategoryConfig categoryConfig, @PathVariable("masterId") String masterId) {
        SillyMaster master = categoryConfig.getSillyReadService().getMaster(masterId);
        SillyAssert.notNull(master, "主数据信息不存在，无法删除");
        categoryConfig.getSillyWriteService().delete(masterId, master.getProcessId());
        return SillyR.ok("数据删除成功");
    }

    /**
     * 任务保存/提交
     *
     * @param categoryConfig
     * @param params
     * @return
     */
    @PostMapping("/saveMap")
    public SillyR save(SillyCategoryConfig categoryConfig, @RequestBody Map<String, Object> params) {
        Object o = categoryConfig.getSillyWriteService().saveTaskMap(params);
        return SillyR.ok(o);
    }

    /**
     * 主数据信息
     *
     * @param categoryConfig
     * @param masterId
     * @return
     */
    @GetMapping("/{masterId}")
    public SillyR get(SillyCategoryConfig categoryConfig, @PathVariable("masterId") String masterId) {
        Object o = categoryConfig.getSillyReadService().getMasterMap(masterId);
        return SillyR.ok(o);
    }

    /**
     * root数据信息
     *
     * @param categoryConfig
     * @param id
     * @return
     */
    @GetMapping(value = "/root")
    public SillyR root(SillyCategoryConfig categoryConfig, @RequestParam("id") String id) {
        SillyReadService sillyReadService = categoryConfig.getSillyReadService();
        Map<String, Object> map = sillyReadService.queryRoot(id);
        return SillyR.ok().put("data", map);
    }

    /**
     * 当前任务操作信息
     *
     * @param categoryConfig
     * @param id
     * @param taskId
     * @return
     */
    @GetMapping(value = "/option")
    public SillyR option(SillyCategoryConfig categoryConfig, @RequestParam("id") String id
            , @RequestParam(value = "taskId") String taskId) {
        SillyReadService sillyReadService = categoryConfig.getSillyReadService();
        List<SillyProcessNodeOptionProperty> option = sillyReadService.option(taskId, id);
        return SillyR.ok(option);
    }

    /**
     * 责任人流转
     *
     * @param categoryConfig
     * @param taskId
     * @param userId
     * @param reason
     * @return
     */
    @PostMapping("/flow")
    public SillyR flow(SillyCategoryConfig categoryConfig,
                       @RequestParam("taskId") String taskId,
                       @RequestParam("userId") String userId,
                       @RequestParam("reason") String reason) {
        categoryConfig.getSillyWriteService().changeUser(taskId, userId, reason);
        return SillyR.ok("人员流转成功");
    }

    /**
     * 任务驳回
     *
     * @param categoryConfig
     * @param taskId
     * @param nodeKey
     * @param reason
     * @param userId
     * @return
     */
    @PostMapping("/reject")
    public SillyR reject(SillyCategoryConfig categoryConfig,
                         @RequestParam("taskId") String taskId,
                         @RequestParam("nodeKey") String nodeKey,
                         @RequestParam("reason") String reason,
                         @RequestParam(value = "userId", required = false) String userId) {
        categoryConfig.getSillyWriteService().reject(taskId, nodeKey, reason, userId);
        return SillyR.ok("任务驳回成功");
    }

}
