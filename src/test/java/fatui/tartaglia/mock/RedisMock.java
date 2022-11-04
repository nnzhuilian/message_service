package fatui.tartaglia.mock;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import fatui.tartaglia.dao.RedisDao;
import fatui.tartaglia.dao.RedisDaoImpl;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ：tartaglia
 * @description：mock redis
 * @date ：Created in 2021/11/1 下午3:57
 */

@Configuration
public class RedisMock {
    private static Map<String, Queue<String>> jedisList = Maps.newConcurrentMap();

    @Bean
    @Primary
    public RedisDao redisDao() {
        RedisDaoImpl redisDao = new RedisDaoImpl();
        redisDao.setJedisPool(jedisPool());
        return redisDao;
    }

    @Bean
    @Primary
    public JedisPool jedisPool() {
        JedisPool jedisPool = Mockito.mock(JedisPool.class);
        Jedis jedis = Mockito.mock(Jedis.class);
        Mockito.when(jedis.llen(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    if (jedisList.size() == 0 ) {
                        Thread.sleep(1000); // 防止轮询该方法 answer lambda不断创造创造对象导致GC
                        return 0L;
                    } else {
                        Thread.sleep(50);
                        return jedisList.get(invocation.getArgument(0)) == null ? 0L :
                                (long) jedisList.get(invocation.getArgument(0)).size();
                    }
                });
        Mockito.when(jedis.rpush(Mockito.anyString(), Mockito.anyString())).thenAnswer(invocation -> {
            if (jedisList.get(invocation.getArgument(0)) == null) {
                jedisList.put(invocation.getArgument(0), new LinkedBlockingQueue<>());
            }
            return jedisList.get(invocation.getArgument(0)).offer(invocation.getArgument(1)) ? 1L : 0L;
        });
        Mockito.when(jedis.lpop(Mockito.anyString()))
                .thenAnswer(invocation -> jedisList.get(invocation.getArgument(0)).poll());
        Mockito.when(jedisPool.getResource()).thenReturn(jedis);
        return jedisPool;
    }
}
