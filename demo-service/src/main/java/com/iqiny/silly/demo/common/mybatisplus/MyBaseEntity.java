package com.iqiny.silly.demo.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iqiny.silly.common.util.StringUtils;
import com.iqiny.silly.mybatisplus.baseentity.BaseMyBaseEntity;

import java.util.Date;

/**
 * MybatisPlus 集成基础实体数据
 *
 * @param <T>
 */
public abstract class MyBaseEntity<T extends Model<?>> extends BaseMyBaseEntity<T> {

    /**
     * 主键
     */
    @TableId
    protected String id;
    /**
     * 删除标记  1：已删除  0：正常
     */
    @TableLogic
    @JsonIgnore
    protected String delFlag;

    @Override
    public void preInsert() {
        if (isNewRecord()) {

        }
        this.createDate = new Date();

        this.updateDate = new Date();
    }

    @Override
    public void preUpdate() {
        this.updateDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 判断是否为新数据
     */
    @JsonIgnore
    public boolean isNewRecord() {
        return StringUtils.isEmpty(id);
    }
}
