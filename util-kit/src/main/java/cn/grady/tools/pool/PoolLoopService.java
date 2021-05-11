package cn.grady.tools.pool;

import cn.grady.tools.disruptorkit.Startable;
import cn.grady.tools.enumeration.ThreadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * @author grady
 * @version 1.0, on 0:54 2021/5/12.
 */
@Service
public class PoolLoopService implements Startable {
    private static final Logger logger = LoggerFactory.getLogger(PoolLoopService.class);

    //最大线程数
    private static final int MAX_THREAD_COUNT = 3;

    //
    private ThreadPoolTaskScheduler scheduler;

    @Override
    public void startup() {
        logger.info("start the loop of thread pool service ");

        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setPoolSize(MAX_THREAD_COUNT);
        this.scheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.scheduler.setThreadNamePrefix(ThreadType.poolTask.getValue());
        this.scheduler.afterPropertiesSet();

        // loop for threadpool to start
//        this.scheduler.scheduleWithFixedDelay(null,1000);
        //.... do something

        //TODO: 2021/5/12 线程补全

    }

    @Override
    public void shutdown() {

    }

    @Override
    public int getOrder() {
        return 0;
    }


}
