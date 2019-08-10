package cn.scauaie.cache;

import java.util.Map;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-03-12 16:59
 */
public interface RedisHash {

    Long set(String key, String field, String value);

    String get(String hash, String field);

    Long del(String hash, String field);

    Map<String, String> getAll(String field);

}
