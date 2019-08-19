package cn.scauaie.service.impl;

import cn.scauaie.config.RedisConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class CacheServiceRedisImplTest {

    Jedis jedis;

    @Before
    public void before() {
        JedisPool jedisPool = new RedisConfig().jedisPool();
        jedis = jedisPool.getResource();
    }

    @Test
    public void hgetAll() {

    }

    @Test
    public void set() {
        System.out.println(jedis.set("test", "3333"));
        jedis.close();
    }

    @Test
    public void del() {
    }

    @Test
    public void get() {
    }

    @Test
    public void lpush() {
    }

    @Test
    public void rpush() {
    }

    @Test
    public void lpop() {
    }

    @Test
    public void rpop() {
    }

    @Test
    public void llen() {
    }

    @Test
    public void zadd() {
    }

    @Test
    public void zrangeWithScores() {
    }

    @Test
    public void zrank() {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(jedis.zrank("queue:zkb", "167"));
        jedis.close();
    }

    @Test
    public void zremrangeByRank() {
    }

    @Test
    public void expire() {
        System.out.println(jedis.expire("queue:zkb", 33));
    }
}