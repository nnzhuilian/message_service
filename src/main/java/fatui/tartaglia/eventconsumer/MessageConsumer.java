package fatui.tartaglia.eventconsumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import fatui.tartaglia.service.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：tartaglia
 * @description：消费任务程序启动配置
 * @date ：Created in 2021/7/12 下午5:01
 */
@Service
@Slf4j
public class MessageConsumer implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private MessageService messageService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(() -> {
            while (true) {
                messageService.consumeMessage();
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
