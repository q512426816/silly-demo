package com.iqiny.silly.demo.reimbursement.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iqiny.silly.demo.common.mybatisplus.MySillyVariable;


@TableName("silly_reimbursement_variable")
public class ReimbVariable extends MySillyVariable<ReimbVariable> {

	private static final long serialVersionUID = 1L;

	@Override
	public String usedCategory() {
		return ReimbMaster.CATEGORY;
	}
}
