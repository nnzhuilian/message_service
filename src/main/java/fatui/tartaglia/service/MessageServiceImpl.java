package fatui.tartaglia.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fatui.tartaglia.bean.Message;
import fatui.tartaglia.enums.MessageType;
import fatui.tartaglia.handler.MessageHandler;


import lombok.extern.slf4j.Slf4j;

/**
 * @author ：tartaglia
 * @description：消息接收实现类
 * @date ：Created in 2021/6/30 下午5:03
 */

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    @Qualifier("messageType2MessageHandlerMap")
    Map<MessageType, Set<MessageHandler>> messageTypeMessageHandlerMap;

    @Autowired
    List<MessageHandler> messageHandlerList;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void receiveMessage(String messageStr) {
        Message message = JsonUtils.fromJson(messageStr, Message.class);
        Validate.notNull(message.getMessageType(), "信息类型不得为空！");
        Set<MessageHandler> messageHandlers = messageTypeMessageHandlerMap.get(message.getMessageType());
        messageHandlers.forEach(handler -> {
            handler.saveMessageStrAtTail(messageStr);
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void consumeMessage() {
        messageHandlerList.forEach(messageHandler -> {
            if (messageHandler.getMessageListLength() > 0) {
                try {
                    if (messageHandler.getRateLimiter()) {
                        String messageStr = messageHandler.getMessageStr();
                        messageHandler.doHandleMessage(messageStr);
                    }
                } catch (Exception e) {
                    // 此处可把失败的messageStr落库到数据库，用于重发或者排查
                    log.info("消息处理失败");
                }
            }
        });
    }

}
