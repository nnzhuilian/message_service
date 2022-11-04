package fatui.tartaglia.handler;

import java.util.List;

import fatui.tartaglia.enums.MessageType;

/**
 * @author ：tartaglia
 * @description：消息处理接口
 * @date ：Created in 2021/6/30 下午2:00
 */
public interface MessageHandler {

    /**
     * 消息处理
     *
     * @param messageStr 消息json字符串
     */
    void doHandleMessage(String messageStr);

    /**
     * 处理器支持的消息类型
     *
     * @return 处理器支持的消息类型
     */
    List<MessageType> supportType();

    /**
     * 从redis获取消息json字符串
     *
     * @return 消息json字符串
     */
    String getMessageStr();

    /**
     * 新消息加入队列
     *
     * @param messageStr 消息
     * @return 消息队列长度
     */
    Long saveMessageStrAtTail(String messageStr);

    /**
     * 获取消息队列长度
     *
     * @return size 消息队列长度
     */
    Long getMessageListLength();

    /**
     * 获取令牌
     *
     * @return 是否获取成功
     */
    Boolean getRateLimiter();

}
