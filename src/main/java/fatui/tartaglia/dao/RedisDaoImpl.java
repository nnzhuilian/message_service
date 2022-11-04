package fatui.tartaglia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ：tartaglia
 * @description：redis实现类
 * @date ：Created in 2021/6/30 下午5:15
 */
@Slf4j
@Service
@Setter
public class RedisDaoImpl implements RedisDao {

    @Value("${redis.prefix:goodcoder}")
    private String prefix;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Long rpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpush(genKey(key), value);
        }
    }

    @Override
    public Long llen(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.llen(genKey(key));
        }
    }

    @Override
    public String lpop(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpop(genKey(key));
        }
    }

    private String genKey(String key) {
        return prefix + key;
    }
}
