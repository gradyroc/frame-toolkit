package cn.grady.tools.pool;

import cn.grady.tools.disruptorkit.Startable;
import cn.grady.tools.common.enumeration.ThreadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 0:54 2021/5/12.
 */
@Service
public class PoolLoopService implements Startable {
    private static final Logger logger = LoggerFactory.getLogger(PoolLoopService.class);

    //最大线程数
    private static final int MAX_THREAD_COUNT = 3;

    @Autowired
    private List<AbstractPoolThread> list;

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

        for (AbstractPoolThread apt : list) {

            try {
                this.scheduler.scheduleWithFixedDelay(apt, apt.getIntervalMs());
                logger.info("   ======>> start：{}", apt.getClass().getSimpleName());

            } catch (Throwable t) {
                throw t;
            }
        }

        logger.info("loop thread start over !");


    }

    @Override
    public void shutdown() {
        AbstractPoolThread.shutDown();

        try {
            this.scheduler.shutdown();
            for (;;){
                if (this.scheduler.getScheduledExecutor().isTerminated()){
                    break;
                }
                Thread.sleep(100);
            }
            logger.info("stop the loop thread over !");
        } catch (InterruptedException e) {
            logger.error("stop the loop thread failed !",e);

        }
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
