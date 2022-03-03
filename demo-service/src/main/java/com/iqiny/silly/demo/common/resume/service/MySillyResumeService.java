package com.iqiny.silly.demo.common.resume.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iqiny.silly.common.SillyConstant;
import com.iqiny.silly.common.util.StringUtils;
import com.iqiny.silly.core.read.MySillyMasterTask;
import com.iqiny.silly.core.resume.SillyResume;
import com.iqiny.silly.core.resume.SillyResumeService;
import com.iqiny.silly.demo.common.resume.entity.MySillyResume;
import com.iqiny.silly.demo.common.resume.dao.MySillyResumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MySillyResumeService implements SillyResumeService {

    @Autowired
    private MySillyResumeDao sillyResumeDao;

    private final Map<String, String> handleMap = new HashMap<>();


    @Override
    public String makeResumeHandleInfo(String handleType, String nextUserIds, String taskName, String content) {
        if (StringUtils.isNotEmpty(content)) {
            taskName += ("，" + content);
        }
        final String handleName = handleMap.getOrDefault(handleType, "");
        String nowUserName = "a";
        String nowUserOfficeName = "C";
        StringBuilder nextUserName = new StringBuilder();
        StringBuilder nextUserOfficeName = new StringBuilder();
        if (StringUtils.isEmpty(nextUserIds)) {
            return String.format("【%s】 (%s) %s-%s。", nowUserOfficeName, nowUserName, handleName, taskName);
        }

        String spStr = "、";
        int index = nextUserName.lastIndexOf(spStr);
        if (index > 0) {
            nextUserName.deleteCharAt(index);
        }
        index = nextUserOfficeName.lastIndexOf(spStr);
        if (index > 0) {
            nextUserOfficeName.deleteCharAt(index);
        }

        if (StringUtils.isEmpty(nextUserOfficeName.toString())) {
            Object[] arr = new String[]{nowUserOfficeName, nowUserName, handleName, taskName, nextUserName.toString()};
            return String.format("【%s】 (%s) %s-%s，下一步由 (%s)进行处置。", arr);
        }
        Object[] arr = new String[]{nowUserOfficeName, nowUserName, handleName, taskName, nextUserOfficeName.toString(), nextUserName.toString()};
        return String.format("【%s】 (%s) %s-%s，下一步由【%s】 (%s)进行处置。", arr);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(SillyResume sillyResume) {
        if (sillyResume instanceof MySillyResume) {
            MySillyResume resume = (MySillyResume) sillyResume;
            resume.setHandleDate(new Date());
            resume.preInsert();
            resume.insert();
        } else {
            throw new RuntimeException("流程履历对象类型错误");
        }
    }

    @Override
    public List<? extends SillyResume> selectList(String masterId, String businessType) {
        MySillyResume resume = new MySillyResume();
        resume.setBusinessId(masterId);
        resume.setBusinessType(businessType);
        QueryWrapper<MySillyResume> qw = new QueryWrapper<>();
        qw.setEntity(resume);
        qw.orderByAsc("HANDLE_DATE");
        return new MySillyResume().selectList(qw);
    }

    @Override
    public void init() {
        handleMap.put(SillyConstant.SillyResumeType.PROCESS_TYPE_NEXT, "完成任务");
        handleMap.put(SillyConstant.SillyResumeType.PROCESS_TYPE_BACK, "执行任务驳回");
        handleMap.put(SillyConstant.SillyResumeType.PROCESS_TYPE_CLOSE, "执行流程关闭");
        handleMap.put(SillyConstant.SillyResumeType.PROCESS_TYPE_FLOW, "执行任务流转");
        handleMap.put(SillyConstant.SillyResumeType.PROCESS_TYPE_START, "执行流程启动");
    }

    /**
     * 根据履历获取 历史处置任务
     *
     * @param category
     * @param userId
     */
    public List<MySillyMasterTask> getHistoryMasterTask(String category, String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("category", category);
        param.put("userId", userId);
        return sillyResumeDao.getHistoryMasterTask(param);
    }
}
