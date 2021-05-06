package com.tinyurl.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.tinyurl.cache.TinyUrlCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地缓存实现
 *
 * @author Terry
 * @since 2021/5/6 15:30
 */
//@Primary 通过此注解来指定本地还是Redis
@Service
public class TinyUrlLocalCacheServiceImpl implements TinyUrlCacheService {

    @Autowired
    private LoadingCache<String, String> readTinyUrlCache;

    @Autowired
    private Cache<String, String> writeTinyUrlCache;

    @Override
    public void putWriteCache(String originUrl, String tinyCode) {
        writeTinyUrlCache.put(originUrl, tinyCode);
    }

    @Override
    public String getWriteCache(String originUrl) {
        return writeTinyUrlCache.getIfPresent(originUrl);
    }

    @Override
    public String getReadCache(String tinyCode) {
        return readTinyUrlCache.get(tinyCode);
    }
}
