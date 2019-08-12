package cn.scauaie.service;

import java.util.Map;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-12 13:27
 */
public interface CacheService {

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String field);

    Map<String, String> hgetAll(String key);

    String set(String key, String value);

    String get(String key);

    Long del(String key);

    Long expire(String key, int seconds);

}
