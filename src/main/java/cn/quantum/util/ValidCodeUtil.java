package cn.quantum.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ValidCodeUtil {
    // 移除监听器
    private static final RemovalListener<String, String> listener = new RemovalListener<String, String>() {
        public void onRemoval(RemovalNotification<String, String> notification) {
            log.info("[" + notification.getKey() + ":" + notification.getValue() + "] is removed!");
        }
    };

    private static final Cache<String, String> validCodeCahe = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public synchronized static void put(String key, String value){
        validCodeCahe.put(key, value);
    }

    public synchronized static String get(String key){
        return validCodeCahe.getIfPresent(key);
    }

    public synchronized static void remove(String key){
        validCodeCahe.invalidate(key);
    }

    public synchronized static boolean isExist(String key){
        return validCodeCahe.getIfPresent(key) != null;
    }
}
