package fatui.tartaglia.handler;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fatui.tartaglia.bean.TextMessage;
import fatui.tartaglia.enums.MessageType;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：tartaglia
 * @description：短信消息处理器
 * @date ：Created in 2021/7/5 上午11:19
 */

@Service
@Slf4j
public class TextMessageHandler extends AbstractMessageHandler {
    private static final String HANDLER_KEY = "TextMessageHandler";

    private static volatile RateLimiter rateLimiter;

    @Value("${ratelimiter.text.ratePerSecond:1}")
    private double rate;

    @Override
    public void sendMessage(String messageStr) {
        TextMessage message = JsonUtils.fromJson(messageStr, TextMessage.class);
        Validate.notNull(message, "消息格式错误！");
        log.info("模拟短信发送，电话号码：{}，用户名：{}，内容：{}"
                , message.getPhoneNumber()
                , message.getUserName()
                , message.getContent());
    }

    @Override
    public List<MessageType> supportType() {
        return Lists.newArrayList(MessageType.TEXT);
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
