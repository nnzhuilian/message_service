package fatui.tartaglia.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fatui.tartaglia.enums.MessageType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author ：tartaglia
 * @description：消息处理器配置类
 * @date ：Created in 2021/7/5 下午4:40
 */
@Configuration
public class MessageHandlerConfig {
    @Bean("messageType2MessageHandlerMap")
    public Map<MessageType, Set<MessageHandler>> genMessageType2MessageHandlerMap(
            @Autowired List<MessageHandler> messageHandlerList) {
        Map<MessageType, Set<MessageHandler>> messageType2MessageHandlerMap = Maps.newHashMap();
        for (MessageHandler messageHandler : messageHandlerList) {
            messageHandler.supportType().forEach(messageType -> {
                if (!messageType2MessageHandlerMap.containsKey(messageType)) {
                    messageType2MessageHandlerMap.put(messageType, Sets.newHashSet());
                }
                messageType2MessageHandlerMap.get(messageType).add(messageHandler);
            });
        }
        return messageType2MessageHandlerMap;
    }
}
