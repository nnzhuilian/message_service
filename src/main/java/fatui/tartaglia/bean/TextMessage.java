package fatui.tartaglia.bean;

import lombok.Data;

/**
 * @author ：tartaglia
 * @description：短信消息
 * @date ：Created in 2021/10/29 下午4:29
 */
@Data
public class TextMessage extends Message {
    // 电话号码
    private String phoneNumber;
}
