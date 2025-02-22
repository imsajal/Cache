package org.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.demo.CacheType.*;

public class CacheManager implements  Cache{

    Cache cache;
    int capacity;
    Map<CacheType, Cache> cacheByType = new HashMap<>();

    public CacheManager(CacheType cacheType, int capacity) {
        this.cache = switchCache(cacheType);
        this.capacity = capacity;
    }

    Cache switchCache(CacheType cacheType){
        Cache cache = null;
        if(cacheByType.containsKey(cacheType))
            cache = cacheByType.get(cacheType);
        else {
            switch (cacheType) {
                case LRU:
                    cache = cacheByType.get(LRU);
                    break;
                case MRU:
                    cache = cacheByType.get(MRU);
                    break;
                case FIFO:
                    cache = cacheByType.get(FIFO);
                    break;
                case LFU:
                    cache = cacheByType.get(LFU);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Cache Type - " + cacheType.toString());
            }
            if(Objects.nonNull(cache))
            cacheByType.put(cacheType, cache);
        }
        return cache;
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public void putOrUpdate(String key, String value) {
       cache.putOrUpdate(key, value);
    }
}
