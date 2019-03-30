package cn.quantum.util;


import cn.quantum.web.vo.TUserVO;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 这个类是用来管理在线token和用户的信息映射的
 *
 * 由于没有redis缓存之类的东西，所以只能用map来暂时地缓存一下
 */
public class TokenManageUtil {
    private static ConcurrentMap tokenMaps = new ConcurrentHashMap();

    public static boolean exists(String token){
        return tokenMaps.containsKey(token);
    }

    public static Object get(String token){
        return tokenMaps.get(token);
    }

    public static void put(String token, TUserVO userInfo){
        tokenMaps.put(token, userInfo);
    }

    public static TUserVO remove(String token){
        return (TUserVO) tokenMaps.remove(token);
    }

}
