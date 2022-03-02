package com.iqiny.silly.demo.reimbursement.service;

import org.springframework.stereotype.Service;

@Service
public class ReimbursementService {

    /**
     * 生成编号
     *
     * @param id
     * @return
     */
    public String generatorCode(String id) {
        // 模拟生成 编号
        return "code: " + id;
    }


}
