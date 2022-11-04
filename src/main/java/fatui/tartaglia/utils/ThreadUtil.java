package fatui.tartaglia.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：tartaglia
 * @description：线程工具类
 * @date ：Created in 2021/8/17 上午11:40
 */
@Configuration
public class ThreadUtil {
    @Value("${thread.corePoolSize:10}")
    private int corePoolSize;

    @Value("${thread.maxmumPoolSize:10}")
    private int maxmumPoolSize;

    @Value("${thread.keepAliveTime:60}")
    private Long keepAliveTime;

    @Value("${thread.capacity:500}")
    private int capacity;

    public ThreadPoolExecutor genThread() {
        return new ThreadPoolExecutor(corePoolSize, maxmumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(capacity), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
