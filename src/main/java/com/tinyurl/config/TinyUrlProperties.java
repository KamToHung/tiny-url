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
     * 读过期时间,默认秒
     */
    private int readTimeOut = 3600;

    /**
     * 写过期时间,默认秒
     */
    private int writeTimeOut = 3600;

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

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public String getTinyHost() {
        return tinyHost;
    }

    public void setTinyHost(String tinyHost) {
        this.tinyHost = tinyHost;
    }
}
