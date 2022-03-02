package com.iqiny.silly.demo.common.cov;

import org.springframework.stereotype.Component;

@Component
public class SillyFileGroupConvertor extends MySillyStringConvertor {

    public static final String NAME = "filegroup";
    public static final String END_WITH_NAME = "fileGroupId";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    protected String fieldNameEndWith() {
        return END_WITH_NAME;
    }

    /**
     * 实际使用可以根据ID获取名称
     *
     * @param fileId
     * @return
     */
    @Override
    protected String getValue(String fileId) {
        return "文件名称：" + fileId;
    }
}
