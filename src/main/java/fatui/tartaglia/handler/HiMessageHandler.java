package fatui.tartaglia.handler;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fatui.tartaglia.bean.HiMessage;
import fatui.tartaglia.enums.MessageType;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：tartaglia
 * @description：hi消息处理器
 * @date ：Created in 2021/7/5 上午11:19
 */

@Service
@Slf4j
public class HiMessageHandler extends AbstractMessageHandler {
    private static final String HANDLER_KEY = "HiMessageHandler";

    private static volatile RateLimiter rateLimiter;

    @Value("${ratelimiter.hi.ratePerSecond:1}")
    private double rate;

    @Override
    public void sendMessage(String messageStr) {
        HiMessage message = JsonUtils.fromJson(messageStr, HiMessage.class);
        Validate.notNull(message, "消息格式错误！");
        log.info("模拟发送hi消息，消息发送群：{}，消息提醒对象：{}，用户名：{}，消息内容：{}"
                , message.getGroup()
                , message.getAccount()
                , message.getUserName()
                , message.getContent());
    }

    @Override
    public List<MessageType> supportType() {
        return Lists.newArrayList(MessageType.Hi);
    }

    @Override
    public String getHanlderKey() {
        return HANDLER_KEY;
    }

    @Override
    public Boolean getRateLimiter() {
        if (rateLimiter == null) {
            synchronized(this.getClass()) {
                if (rateLimiter == null) {
                    rateLimiter = RateLimiter.create(rate);
                }
            }
        }
        return rateLimiter.tryAcquire();
    }
}
