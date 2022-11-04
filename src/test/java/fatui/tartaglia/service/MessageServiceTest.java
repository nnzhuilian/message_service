package fatui.tartaglia.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fatui.tartaglia.AppTest;
import fatui.tartaglia.dao.RedisDao;

/**
 * @author ：tartaglia
 * @description：消息处理测试
 * @date ：Created in 2021/7/23 下午6:21
 */
public class MessageServiceTest extends AppTest {
    @Autowired
    MessageService messageService;

    @Autowired
    RedisDao redisDao;

    @Test
    /**
     * 邮件消息，每间隔5s发送一次
     */
    public void mailMessageTest() throws InterruptedException {
        messageService.receiveMessage("{\"messageType\":\"EMAIL\",\"userName\":\"name\",\"content\":\"mail1\","
                + "\"mailAddress\":\"mailAddress\"}");
        messageService.receiveMessage("{\"messageType\":\"EMAIL\",\"userName\":\"name\",\"content\":\"mail2\","
                + "\"mailAddress\":\"mailAddress\"}");
        messageService.receiveMessage("{\"messageType\":\"EMAIL\",\"userName\":\"name\",\"content\":\"mail3\","
                + "\"mailAddress\":\"mailAddress\"}");

        Thread.sleep(1000L);
        Assert.assertEquals(2L, redisDao.llen("messageMailMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(1L, redisDao.llen("messageMailMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(0L, redisDao.llen("messageMailMessageHandler").longValue());
    }

    @Test
    /**
     * hi消息，每间隔5s发送一次
     */
    public void hiMessageTest() throws InterruptedException {
        messageService.receiveMessage("{\"messageType\":\"Hi\",\"userName\":\"name\",\"content\":\"hi1\","
                + "\"hiGroup\":\"hiGroup\",\"hiAccount\":\"hiAccount\"}");
        messageService.receiveMessage("{\"messageType\":\"Hi\",\"userName\":\"name\",\"content\":\"hi2\","
                + "\"hiGroup\":\"hiGroup\",\"hiAccount\":\"hiAccount\"}");
        messageService.receiveMessage("{\"messageType\":\"Hi\",\"userName\":\"name\",\"content\":\"hi3\","
                + "\"hiGroup\":\"hiGroup\",\"hiAccount\":\"hiAccount\"}");

        Thread.sleep(1000L);
        Assert.assertEquals(2L, redisDao.llen("messageHiMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(1L, redisDao.llen("messageHiMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(0L, redisDao.llen("messageHiMessageHandler").longValue());
    }

    @Test
    /**
     * 短信消息，每间隔5s发送一次
     */
    public void textMessageTest() throws InterruptedException {
        messageService.receiveMessage("{\"messageType\":\"TEXT\",\"userName\":\"name\",\"content\":\"text1\","
                + "\"phoneNumber\":\"1008610086\"}");
        messageService.receiveMessage("{\"messageType\":\"TEXT\",\"userName\":\"name\",\"content\":\"text2\","
                + "\"phoneNumber\":\"1008610086\"}");
        messageService.receiveMessage("{\"messageType\":\"TEXT\",\"userName\":\"name\",\"content\":\"text3\","
                + "\"phoneNumber\":\"1008610086\"}");

        Thread.sleep(1000L);
        Assert.assertEquals(2L, redisDao.llen("messageTextMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(1L, redisDao.llen("messageTextMessageHandler").longValue());

        Thread.sleep(5000L);
        Assert.assertEquals(0L, redisDao.llen("messageTextMessageHandler").longValue());
    }

}
