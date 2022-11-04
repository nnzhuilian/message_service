package fatui.tartaglia.enums;

/**
 * @author ：tartaglia
 * @description：消息类型
 * @date ：Created in 2021/6/30 下午2:16
 */
public enum MessageType {

    Hi("hi报警"),
    EMAIL("邮件报警"),
    TEXT("短信报警");

    private String desc;

    MessageType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
