package com.iqiny.silly.demo.common.cov;

import com.iqiny.silly.common.util.SillyReflectUtil;
import com.iqiny.silly.core.base.core.SillyVariable;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

/**
 * 数据Value: String
 * map: {key: "key", value: "1"}
 */
public abstract class MySillyStringConvertor extends MySillyAutoStringCovertor {

    protected String getNameKey(String key) {
        String nameKey = key;
        if (key.endsWith("Id")) {
            nameKey = key.substring(0, key.lastIndexOf("Id"));
        }
        return nameKey + "Name";
    }

    @Override
    public String convert(Map<String, Object> varMap, String key, String value) {
        final String id = super.convert(varMap, key, value);

        final String nameKey = getNameKey(key);
        if (varMap.get(nameKey) == null) {
            varMap.put(nameKey, getValue(id));
        }

        return id;
    }

    @Override
    public List<SillyVariable> makeSaveVariable(SillyVariable variable) {
        final List<SillyVariable> sillyVariables = super.makeSaveVariable(variable);
        beforeMakeAddOne(sillyVariables, variable);
        return sillyVariables;
    }

    protected void beforeMakeAddOne(List<SillyVariable> list, SillyVariable variable) {
        final String variableText = getValue(variable.getVariableText());
        if (variableText != null) {
            final String variableName = variable.getVariableName();
            final SillyVariable copy = SillyReflectUtil.newInstance(variable.getClass());
            BeanUtils.copyProperties(variable, copy);
            final String nameKey = getNameKey(variableName);

            copy.setVariableName(nameKey);
            copy.setVariableText(variableText);
            copy.setVariableType(super.name());
            list.add(copy);
        }
    }

    protected abstract String getValue(String oVariable);

}
