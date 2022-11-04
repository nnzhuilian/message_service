package fatui.tartaglia.bean;

import lombok.Data;

/**
 * @author ：tartaglia
 * @description：邮件消息
 * @date ：Created in 2021/10/28 下午8:37
 */
@Data
public class MailMessage extends Message {
    // 邮箱地址
    private String address;
}
