package com.tinyurl.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
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

    private static TinyUrlProperties tinyUrlProperties;

    /**
     * 短链接读缓存 K:Base58(id) V:originUrl
     */
    public static LoadingCache<String, String> readTinyUrlCache;

    /**
     * 用于避免一段时间内客户重复获取生成短链接
     * 短链接写缓存 K:originUrl V:id
     */
    public static LoadingCache<String, String> writeTinyUrlCache;

    public TinyUrlCacheConfig(TinyUrlProperties tinyUrlProperties) {
        TinyUrlCacheConfig.tinyUrlProperties = tinyUrlProperties;
        TinyUrlCacheConfig.writeTinyUrlCache = Caffeine.newBuilder()
                .expireAfterWrite(tinyUrlProperties.getWriteTimeOut(), TimeUnit.SECONDS)
                .build(key -> {
                    // 查询数据库返回originUrl
                    return null;
                });
        TinyUrlCacheConfig.readTinyUrlCache = Caffeine.newBuilder()
                .expireAfterWrite(tinyUrlProperties.getReadTimeOut(), TimeUnit.SECONDS)
                .build(originUrl -> {
                    // 查询数据库返回Base58(id)
                    return null;
                });
    }
}
