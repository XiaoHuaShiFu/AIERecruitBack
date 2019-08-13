package cn.scauaie.config;

import cn.scauaie.util.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * 描述:配置Redis
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 20:38
 */
@Configuration
public class RedisConfig {

    /**
     * JedisPool单例
     * @return JedisPool
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(Objects.requireNonNull(PropertiesUtils.getProperty("redis.maxIdle", "redis.properties"))));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(Objects.requireNonNull(PropertiesUtils.getProperty("redis.maxTotal", "redis.properties"))));
        return new JedisPool(jedisPoolConfig,
                PropertiesUtils.getProperty("redis.url", "redis.properties"),
                Integer.parseInt(Objects.requireNonNull(PropertiesUtils.getProperty("redis.port", "redis.properties"))),
                Integer.parseInt(Objects.requireNonNull(PropertiesUtils.getProperty("redis.timeout", "redis.properties"))),
                PropertiesUtils.getProperty("redis.password", "redis.properties"));
    }

}
