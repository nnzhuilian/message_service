package fatui.tartaglia.handler;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fatui.tartaglia.bean.MailMessage;
import fatui.tartaglia.enums.MessageType;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：tartaglia
 * @description：邮件消息处理器
 * @date ：Created in 2021/7/5 上午11:18
 */

@Service
@Slf4j
public class MailMessageHandler extends AbstractMessageHandler {
    private static final String HANDLER_KEY = "MailMessageHandler";

    private static volatile RateLimiter rateLimiter;

    @Value("${ratelimiter.mail.ratePerSecond:1}")
    private double rate;

    @Override
    public void sendMessage(String messageStr) {
        MailMessage message = JsonUtils.fromJson(messageStr, MailMessage.class);
        Validate.notNull(message, "消息格式错误！");
        log.info("模拟邮件发送，邮箱地址：{}，用户名：{}，内容：{}"
                , message.getAddress()
                , message.getUserName()
                , message.getContent());
    }

    @Override
    public List<MessageType> supportType() {
        return Lists.newArrayList(MessageType.EMAIL);
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

    @Override
    public String getHanlderKey() {
        return HANDLER_KEY;
    }

}
