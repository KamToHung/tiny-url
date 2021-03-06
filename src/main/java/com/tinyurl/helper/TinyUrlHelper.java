package com.tinyurl.helper;

import com.tinyurl.cache.TinyUrlCacheService;
import com.tinyurl.config.TinyUrlProperties;
import com.tinyurl.entity.TinyUrl;
import com.tinyurl.service.TinyUrlService;
import com.tinyurl.util.Base58Utils;
import com.tinyurl.util.SnowFlakeUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * tiny_url帮助类
 *
 * @author Terry
 * @since 2021/4/17 17:34
 */
@Component
public class TinyUrlHelper {

    private static TinyUrlProperties tinyUrlProperties;

    private static TinyUrlService tinyUrlService;

    private static TinyUrlCacheService tinyUrlCacheService;

    private static SnowFlakeUtils snowFlakeUtils;

    public TinyUrlHelper(TinyUrlProperties tinyUrlProperties,
                         TinyUrlService tinyUrlService,
                         TinyUrlCacheService tinyUrlCacheService) {
        TinyUrlHelper.tinyUrlProperties = tinyUrlProperties;
        TinyUrlHelper.tinyUrlService = tinyUrlService;
        TinyUrlHelper.tinyUrlCacheService = tinyUrlCacheService;
        TinyUrlHelper.snowFlakeUtils = new SnowFlakeUtils(tinyUrlProperties.getDatacenterId(), tinyUrlProperties.getMachineId());
    }

    /**
     * 生成tiny_url
     * 设置个本地缓存，有效时间内如果有重复的url则使用上次的id
     *
     * @param url
     * @param ttl
     * @param ip
     * @return
     */
    public static String genTinyUrl(String url, int ttl, String ip) {
        String id = tinyUrlCacheService.getWriteCache(url);
        if (id != null) {
            return tinyUrlProperties.getTinyHost() + "/" + id;
        }
        long nextId = snowFlakeUtils.nextId();
        TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setId(nextId);
        tinyUrl.setUrl(url);
        tinyUrl.setCreateDate(new Timestamp(Instant.now().toEpochMilli()));
        if (ttl > 0) {
            tinyUrl.setExpireDate(new Timestamp(Instant.now().toEpochMilli() + ttl * 1000L));
        }
        tinyUrl.setCreateIp(ip);
        tinyUrl.setState(1);
        tinyUrlService.save(tinyUrl);
        String base58Id = Base58Utils.encodeBase58(Base58Utils.encodeNum(nextId));
        tinyUrlCacheService.putWriteCache(url, base58Id);
        return tinyUrlProperties.getTinyHost() + "/" + base58Id;
    }

    /**
     * 根据tinyCode获得url。
     *
     * @param tinyCode
     * @return
     */
    public static String getUrl(String tinyCode) {
        return tinyUrlCacheService.getReadCache(tinyCode);
    }
}
