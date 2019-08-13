package cn.scauaie.service.impl;

import cn.scauaie.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-03-12 16:57
 */
@Repository("cacheService")
public class CacheServiceRedisImpl implements CacheService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 把field添加到缓存里,并添加添加的时间
     *
     * @param key   key
     * @param field field名
     * @return 成功修改行数
     */
    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.hset(key, field, value);
        jedis.close();
        return rowCount;
    }

    /**
     * 删除对应key的对应field的值
     *
     * @param key   key
     * @param field field名
     * @return 成功修改行数
     */
    @Override
    public Long hdel(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.hdel(key, field);
        jedis.close();
        return rowCount;
    }

    /**
     * 获取对应key的对应field的值
     *
     * @param key   key
     * @param field field名
     * @return 获取到的字符串，如果没获取到返回null
     */
    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.hget(key, field);
        jedis.close();
        return value;
    }

    /**
     * 获得对应key的hash列表的映射列表
     *
     * @param key key
     * @return 映射列表
     */
    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> resultMap = jedis.hgetAll(key);
        jedis.close();
        return resultMap;
    }


    /**
     * 对应redis的set操作
     *
     * @param key   key
     * @param value name
     * @return 状态码
     */
    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String code = jedis.set(key, value);
        jedis.close();
        return code;
    }

    /**
     * 删除对应key的值
     *
     * @param key key
     * @return 成功修改行数
     */
    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.del(key);
        jedis.close();
        return rowCount;
    }

    /**
     * 获取对应key的值
     *
     * @param key key
     * @return 获取到的字符串
     */
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 在队列左边插入值
     *
     * @param key key
     * @param values 值
     * @return length 插入后链表中元素的数量。
     */
    @Override
    public Long lpush(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.lpush(key, values);
        jedis.close();
        return length;
    }

    /**
     * 在队列右边插入值
     *
     * @param key key
     * @param values 值
     * @return length 插入后链表中元素的数量。
     */
    @Override
    public Long rpush(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.rpush(key, values);
        jedis.close();
        return length;
    }

    /**
     * 弹出队列最左边的元素
     *
     * @param key key
     * @return value 链表头部的元素
     */
    @Override
    public String lpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }

    /**
     * 弹出队列最右边的元素
     *
     * @param key key
     * @return value 链表尾部的元素
     */
    @Override
    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.rpop(key);
        jedis.close();
        return value;
    }

    /**
     * 获取链表中元素的数量
     *
     * @param key key
     * @return length 链表中元素数量
     */
    @Override
    public Long llen(String key) {
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.llen(key);
        jedis.close();
        return length;
    }


    /**
     * 设置过期时间
     *
     * @param key     key
     * @param seconds seconds
     * @return 影响行数
     */
    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        Long rowCount = jedis.expire(key, seconds);
        jedis.close();
        return rowCount;
    }
}
