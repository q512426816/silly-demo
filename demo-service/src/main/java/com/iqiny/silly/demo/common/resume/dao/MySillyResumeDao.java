package com.iqiny.silly.demo.common.resume.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iqiny.silly.core.read.MySillyMasterTask;
import com.iqiny.silly.demo.common.resume.entity.MySillyResume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MySillyResumeDao extends BaseMapper<MySillyResume> {

    List<MySillyMasterTask> getHistoryMasterTask(Map<String, Object> param);

}
