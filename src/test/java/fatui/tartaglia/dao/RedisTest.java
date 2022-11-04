package fatui.tartaglia.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fatui.tartaglia.AppTest;

/**
 * @author ：tartaglia
 * @description：redis测试
 * @date ：Created in 2021/7/5 下午7:00
 */

public class RedisTest extends AppTest {

    @Autowired
    RedisDao redisDao;

    @Test
    public void llenTest() {
        redisDao.rpush("testKey", "1");
        Long len = redisDao.llen("testKey");
        Assert.assertEquals(1L, len.longValue());
        redisDao.lpop("testKey");
    }

    @Test
    public void rpushTest() {
        redisDao.rpush("testKey", "1");
        String str = redisDao.lpop("testKey");
        Assert.assertEquals("1", str);
    }
}
