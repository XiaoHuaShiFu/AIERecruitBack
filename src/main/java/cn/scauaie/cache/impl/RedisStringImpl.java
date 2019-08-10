package cn.scauaie.cache.impl;

import cn.scauaie.cache.RedisString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-03-12 16:57
 */
@Repository("redisString")
public class RedisStringImpl implements RedisString {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 对应redis的set操作
     * @param key key
     * @param value value
     * @return 状态码
     */
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String code = jedis.set(key, value);
        jedis.close();
        return code;
    }

    /**
     * 获取对应key的值
     *
     * @param key key
     * @return 获取到的字符串
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 删除对应key的值
     *
     * @param key key
     * @return 成功修改行数
     */
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.del(key);
        jedis.close();
        return rowCount;
    }

    /**
     * 设置过期时间
     *
     * @param key key
     * @param seconds seconds
     * @return 影响行数
     */
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.expire(key, seconds);
        jedis.close();
        return rowCount;
    }

}
