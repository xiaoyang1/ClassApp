package cn.quantum.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

// 这个类是用来缓存 共享课程表的id映射的
@Slf4j

public class ClassCacheUtil {

    // 移除监听器
    private static final RemovalListener<String, Integer> listener = new RemovalListener<String, Integer>() {
        public void onRemoval(RemovalNotification<String, Integer> notification) {
            log.info("[" + notification.getKey() + ":" + notification.getValue() + "] is removed!");
        }
    };

    private static final Cache<String, Integer> classCahe = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build();

    public synchronized static void put(String key, Integer value){
        classCahe.put(key, value);
    }

    public synchronized static Integer get(String key){
        return classCahe.getIfPresent(key);
    }

    public synchronized static void remove(String key){
        classCahe.invalidate(key);
    }

    public synchronized static boolean isExist(String key){
        // 会把缓存删除
        return classCahe.getIfPresent(key) == null;
    }
}
