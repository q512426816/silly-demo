package com.iqiny.silly.demo.common.cov;

import com.iqiny.silly.core.config.SillyCurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据Value: String
 * map: {key: "key", value: "1"}
 */
@Component
public class SillyUserConvertor extends MySillyStringConvertor {

    public static final String NAME = "user";
    public static final String END_WITH_NAME = "UserId";

    @Autowired
    private SillyCurrentUserUtil userUtil;

    @Override
    public String name() {
        return NAME;
    }


    @Override
    protected String getValue(String userId) {
        return userUtil.userIdToName(userId);
    }


    @Override
    protected String fieldNameEndWith() {
        return END_WITH_NAME;
    }
}
