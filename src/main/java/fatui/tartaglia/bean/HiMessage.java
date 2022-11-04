package fatui.tartaglia.bean;

import lombok.Data;

/**
 * @author ：tartaglia
 * @description：如流消息
 * @date ：Created in 2021/10/29 下午4:32
 */
@Data
public class HiMessage extends Message {
    // hi群号
    private String group;

    // hi账号
    private String account;
}
