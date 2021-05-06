package com.tinyurl.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.tinyurl.service.TinyUrlService;
import com.tinyurl.util.Base58Utils;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

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
    public Cache<String, String> writeTinyUrlCache(final TinyUrlProperties tinyUrlProperties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(tinyUrlProperties.getWriteTimeOut(), TimeUnit.SECONDS)
                .recordStats().build();
    }


    @Bean("tinyUrlRedisTemplate")
    public StringRedisTemplate tinyUrlRedisTemplate(final TinyUrlProperties tinyUrlProperties,
                                                    final ClientResources clientResources) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory(tinyUrlProperties.getTinyUrlRedis(), clientResources));
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    /**
     * Redis连接工厂
     *
     * @param redisProperties redis配置属性
     * @param clientResources clientResources
     * @return RedisConnectionFactory
     */
    private RedisConnectionFactory redisConnectionFactory(TinyUrlProperties.RedisProperties redisProperties,
                                                          ClientResources clientResources) {
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder;
        if (pool == null) {
            builder = LettuceClientConfiguration.builder();
        } else {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(pool.getMaxActive());
            config.setMaxIdle(pool.getMaxIdle());
            config.setMinIdle(pool.getMinIdle());
            if (pool.getMaxWait() != null) {
                config.setMaxWaitMillis(pool.getMaxWait().toMillis());
            }
            builder = LettucePoolingClientConfiguration.builder().poolConfig(config);
        }
        if (redisProperties.getTimeout() != null) {
            builder.commandTimeout(redisProperties.getTimeout());
        }
        if (redisProperties.getLettuce() != null) {
            RedisProperties.Lettuce lettuce = redisProperties.getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout());
            }
        }
        builder.clientResources(clientResources);
        LettuceClientConfiguration config = builder.build();
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(redisProperties.getHost());
        standaloneConfig.setPort(redisProperties.getPort());
        standaloneConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        standaloneConfig.setDatabase(redisProperties.getDatabase());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfig, config);
        factory.afterPropertiesSet();
        return factory;
    }

}
