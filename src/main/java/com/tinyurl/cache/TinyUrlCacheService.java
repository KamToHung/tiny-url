package com.tinyurl.cache;

/**
 * 缓存Service
 *
 * @author Terry
 * @since 2021/5/6 15:25
 */
public interface TinyUrlCacheService {

    void putWriteCache(String originUrl, String tinyCode);

    String getWriteCache(String originUrl);

    String getReadCache(String tinyCode);
}
