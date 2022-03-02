package com.iqiny.silly.demo.reimbursement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iqiny.silly.demo.common.mybatisplus.MySillyNode;

import java.io.Serializable;
import java.util.List;

@TableName("silly_reimbursement_node")
public class ReimbNode extends MySillyNode<ReimbNode, ReimbVariable> implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
	private List<ReimbVariable> variableList;

	@Override
	public List<ReimbVariable> getVariableList() {
		return variableList;
	}

	@Override
	public void setVariableList(List<ReimbVariable> variableList) {
		this.variableList = variableList;
	}

	@Override
	public String usedCategory() {
		return ReimbMaster.CATEGORY;
	}
}
