package com.codehuan.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存，可有可无，token2小时过期
 *
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
public class CacheUtil {

    /**
     * 预缓存信息
     */
    public static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();
    /**
     * 每个缓存生效时间2小时
     */
    public static final long CACHE_HOLD_TIME_2H = 2 * 60 * 60 * 1000L;

    /**
     * 每个缓存生效时间1小时
     */
    public static final long CACHE_HOLD_TIME_1H = 1 * 60 * 60 * 1000L;


    /**
     * 存放一个缓存对象，默认保存时间2小时
     *
     * @param cacheName
     * @param obj
     */
    public static void put(String cacheName, Object obj) {
        put(cacheName, obj, CACHE_HOLD_TIME_1H);
    }

    /**
     * 存放一个缓存对象，保存时间为holdTime
     *
     * @param cacheName
     * @param obj
     * @param holdTime
     */
    public static void put(String cacheName, Object obj, long holdTime) {
        if (checkCacheName(cacheName)) {
            return;
        }
        CACHE_MAP.put(cacheName, obj);
        //缓存失效时间
        CACHE_MAP.put(cacheName + "_HoldTime", System.currentTimeMillis() + holdTime);
    }

    /**
     * 删除所有缓存
     */
    public static void removeAll() {
        CACHE_MAP.clear();
    }

    /**
     * 删除某个缓存
     *
     * @param cacheName
     */
    public static void remove(String cacheName) {
        CACHE_MAP.remove(cacheName);
        CACHE_MAP.remove(cacheName + "_HoldTime");
    }

    /**
     * 检查缓存对象是否存在，
     * 若不存在，则返回false
     * 若存在，检查其是否已过有效期，如果已经过了则删除该缓存并返回false
     *
     * @param cacheName
     * @return
     */
    public static boolean checkCacheName(String cacheName) {
        Long cacheHoldTime = (Long) CACHE_MAP.get(cacheName + "_HoldTime");
        if (cacheHoldTime == null || cacheHoldTime == 0L) {
            return false;
        }
        if (cacheHoldTime < System.currentTimeMillis()) {
            remove(cacheName);
            return false;
        }
        return true;
    }
}
