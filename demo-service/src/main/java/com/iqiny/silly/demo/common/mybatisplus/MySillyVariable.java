package com.iqiny.silly.demo.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iqiny.silly.core.base.SillyCategory;
import com.iqiny.silly.mybatisplus.baseentity.BaseMySillyVariable;

import java.util.Date;

/**
 * SillyVariable 集成MybatisPlus
 *
 * @param <T>
 */
public abstract class MySillyVariable<T extends Model<T>> extends BaseMySillyVariable<T> implements SillyCategory {

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
        this.updateDate = new Date();
        this.createDate = new Date();
    }

    @Override
    public void preUpdate() {
        this.updateDate = new Date();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
