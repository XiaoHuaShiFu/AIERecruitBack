package cn.scauaie.config;

import cn.scauaie.utils.PropertiesUtil;
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
        PropertiesUtil propertiesUtil = new PropertiesUtil("redis.properties");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(propertiesUtil.getProperty("redis.maxIdle")));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(propertiesUtil.getProperty("redis.maxTotal")));
        return new JedisPool(jedisPoolConfig,
                propertiesUtil.getProperty("redis.url"),
                Integer.parseInt(propertiesUtil.getProperty("redis.port")),
                Integer.parseInt(propertiesUtil.getProperty("redis.timeout")),
                propertiesUtil.getProperty("redis.password"));
    }

}
