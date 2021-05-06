package com.tinyurl.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.tinyurl.service.TinyUrlService;
import com.tinyurl.util.Base58Utils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * tiny-url本地缓存
 * 如果业务不会频繁出现相同url，则不需要设置
 * 多台机器使用redis缓存
 *
 * @author Terry
 * @since 2021/4/17 17:37
 */
@Configuration
public class TinyUrlCacheConfig {

    private final TinyUrlService tinyUrlService;

    public TinyUrlCacheConfig(TinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    /**
     * 短链接读缓存 K:Base58(id) V:originUrl
     *
     * @param tinyUrlProperties
     * @return
     */
    @Bean
    public LoadingCache<String, String> readTinyUrlCache(final TinyUrlProperties tinyUrlProperties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(tinyUrlProperties.getReadTimeOut(), TimeUnit.SECONDS)
                .recordStats()
                .build(tinyCode -> {
                    // 查询数据库返回Base58(id)
                    return tinyUrlService.getUrlById(Base58Utils.decodeNum(Base58Utils.decodeBase58(tinyCode)));
                });
    }

    /**
     * 用于避免一段时间内客户重复获取生成短链接
     * 短链接写缓存 K:originUrl V:Base58(id)
     *
     * @param tinyUrlProperties
     * @return
     */
    @Bean
    public Cache<Object, Object> writeTinyUrlCache(final TinyUrlProperties tinyUrlProperties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(tinyUrlProperties.getWriteTimeOut(), TimeUnit.SECONDS)
                .recordStats().build();
    }

}
