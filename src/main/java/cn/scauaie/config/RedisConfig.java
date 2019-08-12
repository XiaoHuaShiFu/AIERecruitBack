package cn.scauaie.config;

import cn.scauaie.util.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        PropertiesUtils propertiesUtils = new PropertiesUtils("redis.properties");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(propertiesUtils.getProperty("redis.maxIdle")));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(propertiesUtils.getProperty("redis.maxTotal")));
        return new JedisPool(jedisPoolConfig,
                propertiesUtils.getProperty("redis.url"),
                Integer.parseInt(propertiesUtils.getProperty("redis.port")),
                Integer.parseInt(propertiesUtils.getProperty("redis.timeout")),
                propertiesUtils.getProperty("redis.password"));
    }

}
