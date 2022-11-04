package fatui.tartaglia.service;

/**
 * @author ：tartaglia
 * @description：消息接收接口
 * @date ：Created in 2021/6/30 下午4:45
 */
public interface MessageService {

    /**
     * 接收日志消息
     * @param messageContent 消息内容
     */
    void receiveMessage(String messageContent);

    /**
     * 消费消息
     */
    void consumeMessage();

}
