package com.tinyurl.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sun.misc.Unsafe;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置类。
 *
 * @author axeon
 */
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
     * 数据中心
     */
    private long datacenterId = 0;

    /**
     * 机器标识
     */
    private long machineId = 0;

    /**
     * 短链接域名
     */
    private String tinyHost = "http://127.0.0.1:8080";

    /**
     * 短链接Redis缓存
     */
    private RedisProperties tinyUrlRedis = new RedisProperties();

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

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public String getTinyHost() {
        return tinyHost;
    }

    public void setTinyHost(String tinyHost) {
        this.tinyHost = tinyHost;
    }

    public RedisProperties getTinyUrlRedis() {
        return tinyUrlRedis;
    }

    public void setTinyUrlRedis(RedisProperties tinyUrlRedis) {
        this.tinyUrlRedis = tinyUrlRedis;
    }

    public static class RedisProperties extends org.springframework.boot.autoconfigure.data.redis.RedisProperties {
    }

}
