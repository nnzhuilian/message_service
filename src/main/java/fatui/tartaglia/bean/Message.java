package fatui.tartaglia.bean;

import fatui.tartaglia.enums.MessageType;

import lombok.Data;

/**
 * @author ：tartaglia
 * @description：消息
 * @date ：Created in 2021/6/30 下午11:40
 */
@Data
public class Message {
    // 消息类型
    private MessageType messageType;

    // 用户名
    private String userName;

    // 消息内容
    private String content;
}
