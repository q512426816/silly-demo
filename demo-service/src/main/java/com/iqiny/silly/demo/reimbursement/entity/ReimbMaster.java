package com.iqiny.silly.demo.reimbursement.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iqiny.silly.demo.common.mybatisplus.MySillyMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@TableName("silly_reimbursement")
@Data
public class ReimbMaster extends MySillyMaster<ReimbMaster> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 流程类型，一个主类型唯一，不可修改
     */
    public static final String CATEGORY = "silly_reimbursement";

    private String code;
    private BigDecimal money;
    private String fileGroupId;
    private String remarks;


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
        return CATEGORY;
    }

}
