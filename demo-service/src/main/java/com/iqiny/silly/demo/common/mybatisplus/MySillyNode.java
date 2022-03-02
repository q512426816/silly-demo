package com.iqiny.silly.demo.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iqiny.silly.common.util.StringUtils;
import com.iqiny.silly.core.base.SillyCategory;
import com.iqiny.silly.core.base.core.SillyVariable;
import com.iqiny.silly.mybatisplus.baseentity.BaseMySillyNode;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SillyNode 集成MybatisPlus
 *
 * @param <T>
 */
public abstract class MySillyNode<T extends Model<T>, V extends SillyVariable> extends BaseMySillyNode<T, V> implements SillyCategory {
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

    protected String nodeName;

    @TableField(exist = false)
    protected String nodeUserName;

    @Override
    public void preInsert() {
        if (StringUtils.isEmpty(id)) {

        }
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

    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeUserName() {
        return nodeUserName;
    }

    public void setNodeUserName(String nodeUserName) {
        this.nodeUserName = nodeUserName;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getNodeDate() {
        return nodeDate;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public void setNodeDate(Date nodeDate) {
        this.nodeDate = nodeDate;
    }


    public void putVariableMap(String key, Object value) {
        if (variableMap == null) {
            variableMap = new LinkedHashMap<>();
        }
        variableMap.put(key, value);
    }

    public void putAllVariableMap(Map<String, Object> map) {
        if (variableMap == null) {
            variableMap = new LinkedHashMap<>();
        }
        variableMap.putAll(map);
    }

}
