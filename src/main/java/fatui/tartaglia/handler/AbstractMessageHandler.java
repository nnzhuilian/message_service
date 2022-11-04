package fatui.tartaglia.handler;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatui.tartaglia.dao.RedisDao;
import fatui.tartaglia.utils.ThreadUtil;

/**
 * @author ：tartaglia
 * @description：消息处理器抽象类
 * @date ：Created in 2021/6/30 下午11:25
 */

@Service
public abstract class AbstractMessageHandler implements MessageHandler {

    private static final String MESSAGE_KEY = "message";

    private static ExecutorService threadPool;

    @Autowired
    RedisDao redisDao;

    @Autowired
    ThreadUtil threadUtil;

    @Override
    public void doHandleMessage(String messageStr) {
        this.getThreadPool().submit(() -> sendMessage(messageStr));
    }

    @Override
    public String getMessageStr() {
        return redisDao.lpop(MESSAGE_KEY + this.getHanlderKey());
    }

    @Override
    public Long saveMessageStrAtTail(String messageStr) {
        Long size = redisDao.rpush(MESSAGE_KEY + this.getHanlderKey(),
                messageStr);
        if (size < 1) {
            throw new RuntimeException("消息录入失败！");
        }
        return size;
    }

    @Override
    public Long getMessageListLength() {
        return redisDao.llen(MESSAGE_KEY + this.getHanlderKey());
    }

    /**
     * 发送消息
     *
     * @param messagestr 消息
     */
    public abstract void sendMessage(String messagestr);

    /**
     * 获取redis key
     *
     * @return redis key
     */
    public abstract String getHanlderKey();

    /**
     * 获取线程池
     *
     * @return 线程池
     */
    private synchronized ExecutorService getThreadPool() {
        if (threadPool == null) {
            threadPool = threadUtil.genThread();
        }
        return threadPool;
    }
}
