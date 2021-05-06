package com.tinyurl.util;

/**
 * RedisKey的工具类。
 */
public class RedisKeyUtils {


    /**
     * key的分割符
     */
    public static final char KEY_SPLITTER = ':';

    /**
     * 拼接
     *
     * @param keyPrefix
     * @param args
     * @return
     */
    public static String buildKey(String keyPrefix, Object... args) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(keyPrefix);
        for (Object arg : args) {
            sb.append(KEY_SPLITTER).append(arg);
        }
        return sb.toString();
    }

}
