package cn.grady.poll;

import cn.grady.enumeration.ThreadType;
import cn.grady.util.Startable;
import cn.grady.util.SysExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rociss
 * @version 1.0, on 0:55 2023/5/2.
 */
@Service
public class SimplePollService implements Startable {


    public static final Logger logger = LoggerFactory.getLogger(SimplePollService.class);

    @Autowired
    private List<AbstractPollThread> list;
    public static final int MAX_THREAD_COUNT =3;

    //句柄
    private ThreadPoolTaskScheduler scheduler;

    @Override
    public void startup() {
        logger.info("启动轮询线程服务。。。。。。。");
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setPoolSize(MAX_THREAD_COUNT);
        this.scheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.scheduler.setThreadNamePrefix(ThreadType.scheduling.getCode());
        this.scheduler.afterPropertiesSet();

        for (AbstractPollThread def : list) {
            try {
                this.scheduler.scheduleWithFixedDelay(def,def.getIntervalMs());
                logger.info("  >>>> 启动：{}",def.getClass().getSimpleName());
            }catch (Throwable t){
                SysExceptionUtil.wrapThrow("启动轮询线程服务失败，",t);
            }
        }

        logger.info("启动轮询线程服务完成");
    }

    @Override
    public void shutdown() {
        AbstractPollThread.shutDown();
        try {
            this.scheduler.shutdown();
            for (;;){
                if (this.scheduler.getScheduledExecutor().isTerminated()){
                    break;
                }
                Thread.sleep(100);
            }
            logger.info("停止轮询线程服务完成");
        } catch (Exception e) {
            logger.error("停止轮询线程服务异常",e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
