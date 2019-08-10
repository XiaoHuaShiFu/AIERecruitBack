package cn.scauaie.cache;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-03-12 16:59
 */
public interface RedisString {

    String set(String key, String value);

    String get(String key);

    Long del(String key);

    Long expire(String key, int seconds);

}
