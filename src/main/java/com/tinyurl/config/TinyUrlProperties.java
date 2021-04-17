package com.tinyurl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类。
 *
 * @author axeon
 */
@Configuration
@ConfigurationProperties(prefix = "com.tinyurl")
public class TinyUrlProperties {

    /**
     * 缓存大小。
     */
    private long size = 100000;

    /**
     * ttl,默认秒
     */
    private int ttl = 3600;

    /**
     * 短链接域名
     */
    private String tinyHost = "http://127.0.0.1";

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getTinyHost() {
        return tinyHost;
    }

    public void setTinyHost(String tinyHost) {
        this.tinyHost = tinyHost;
    }
}
