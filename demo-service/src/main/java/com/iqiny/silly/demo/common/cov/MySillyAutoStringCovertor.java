package com.iqiny.silly.demo.common.cov;

import com.iqiny.silly.common.util.StringUtils;
import com.iqiny.silly.core.convertor.SillyAutoConvertor;
import com.iqiny.silly.core.convertor.SillyStringConvertor;

public abstract class MySillyAutoStringCovertor extends SillyStringConvertor implements SillyAutoConvertor {

    @Override
    public boolean canConvertor(String fieldName, Object value) {
        if (StringUtils.isEmpty(fieldName)) {
            return false;
        }
        return fieldName.endsWith(fieldNameEndWith());
    }

    /**
     * 已特点字符结尾的 作为自动转换依据
     *
     * @return
     */
    protected abstract String fieldNameEndWith();
}
