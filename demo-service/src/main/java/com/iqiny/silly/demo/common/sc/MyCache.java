package com.iqiny.silly.demo.common.sc;

import com.iqiny.silly.core.cache.SillyCache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyCache implements SillyCache {

    /**
     * category: {masterId: {data}}
     */
    private Map<String, Map<String, Object>> cacheMap = new ConcurrentHashMap<>(256);

    @Override
    public <T> T getValue(String category, Object key) {
        Map<String, Object> categoryMap = cacheMap.get(category);
        if (categoryMap == null) {
            return null;
        }

        return (T) categoryMap.get(key.toString());
    }

    @Override
    public <T> T setValue(String category, Object key, Object value) {
        Map<String, Object> categoryMap = cacheMap.computeIfAbsent(category, s -> new ConcurrentHashMap<>());
        return (T) categoryMap.put(key.toString(), value);
    }

    @Override
    public void updatePropertyHandleRootCache(String usedCategory, String masterId, Map<String, Object> updateValue) {
        setValue(usedCategory, masterId, updateValue);
    }

    @Override
    public Map<String, Object> getPropertyHandleRootCache(String usedCategory, String masterId) {
        return getValue(usedCategory, masterId);
    }
}
