package com.iqiny.silly.demo.common.sc;

import com.iqiny.silly.core.base.SillyOrdered;
import com.iqiny.silly.mybatisplus.utils.DefaultSillyQueryUtil;
import org.springframework.stereotype.Component;

@Component
public class MySillyQueryUtil extends DefaultSillyQueryUtil implements SillyOrdered {

    @Override
    public int order() {
        return 100;
    }

    @Override
    protected String limit() {
        return "pageSize";
    }

    @Override
    protected String defaultOrderField() {
        return "CREATE_DATE";
    }

}
