package com.iqiny.silly.demo.common.sc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class SillyR extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public SillyR() {
        put("code", 0);
        put("msg", "success");
    }

    public static SillyR error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static SillyR error(String msg) {
        return error(500, msg);
    }

    public static SillyR error(int code, String msg) {
        SillyR r = new SillyR();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static SillyR ok(String msg) {
        SillyR r = new SillyR();
        r.put("msg", msg);
        return r;
    }

    public static SillyR ok(Object obj) {
        if (obj instanceof IPage) {
            return page((IPage) obj);
        }
        SillyR r = new SillyR();
        r.put("data", obj);
        return r;
    }

    public static SillyR ok(Map<String, Object> map) {
        SillyR r = new SillyR();
        r.putAll(map);
        return r;
    }

    public static SillyR ok() {
        return new SillyR();
    }

    public static SillyR page(IPage<?> page) {
        final SillyR ok = SillyR.ok();
        ok.put("list", page.getRecords());
        ok.put("total", page.getTotal());
        ok.put("pageSize", page.getSize());
        ok.put("pages", page.getPages());
        ok.put("pageNo", page.getCurrent());

        return ok;
    }

    @Override
    public SillyR put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
