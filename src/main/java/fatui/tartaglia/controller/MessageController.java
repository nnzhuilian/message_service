package fatui.tartaglia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fatui.tartaglia.service.MessageService;

/**
 * @author ：tartaglia
 * @description：消息接口
 * @date ：Created in 2021/6/30 下午11:03
 */

@RequestMapping("/message")
@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    /**
     * 模拟消息录入接口
     * @param messageContext 消息上下文 Message的Json
     * @return 录入结果
     */
    @RequestMapping(path = {"/send"}, method = {RequestMethod.GET})
    @ResponseBody
    public String sendMessage(@RequestParam(value = "messageContext") String messageContext) {
        try{
            messageService.receiveMessage(messageContext);
            return "消息录入成功！";
        }catch (Exception e){
            return "消息录入失败！";
        }
    }

}
