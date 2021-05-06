package com.tinyurl.cache.impl;

import com.tinyurl.cache.TinyUrlCacheService;
import com.tinyurl.config.TinyUrlProperties;
import com.tinyurl.service.TinyUrlService;
import com.tinyurl.util.Base58Utils;
import com.tinyurl.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存实现
 *
 * @author Terry
 * @since 2021/5/6 15:39
 */
@Service
@Primary
public class TinyUrlRedisCacheServiceImpl implements TinyUrlCacheService {

    private static final String KEY_PREFIX = "TinyUrl";

    @Autowired
    private StringRedisTemplate tinyUrlRedisTemplate;

    @Autowired
    private TinyUrlProperties tinyUrlProperties;

    @Autowired
    private TinyUrlService tinyUrlService;

    @Override
    public void putWriteCache(String originUrl,
                              String tinyCode) {
        tinyUrlRedisTemplate.opsForValue().set(RedisKeyUtils.buildKey(KEY_PREFIX, originUrl), tinyCode, tinyUrlProperties.getWriteTimeOut(), TimeUnit.SECONDS);
    }

    @Override
    public String getWriteCache(String originUrl) {
        return tinyUrlRedisTemplate.opsForValue().get(RedisKeyUtils.buildKey(KEY_PREFIX, originUrl));
    }

    @Override
    public String getReadCache(String tinyCode) {
        String originUrl = tinyUrlRedisTemplate.opsForValue().get(RedisKeyUtils.buildKey(KEY_PREFIX, tinyCode));
        if (originUrl == null) {
            originUrl = tinyUrlService.getUrlById(Base58Utils.decodeNum(Base58Utils.decodeBase58(tinyCode)));
            if (originUrl != null) {
                tinyUrlRedisTemplate.opsForValue().set(RedisKeyUtils.buildKey(KEY_PREFIX, tinyCode), originUrl, tinyUrlProperties.getReadTimeOut(), TimeUnit.SECONDS);
            }
        }
        return originUrl;
    }

}
