package cn.grady.poll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rociss
 * @version 1.0, on 23:18 2023/5/1.
 */
public class AbstractPollThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(AbstractPollThread.class);

    public static   boolean isIsShuttingDown() {
        return isShuttingDown;
    }

    public static void shutDown() {
        isShuttingDown = true;
    }

    private static volatile boolean isShuttingDown = false;



    @Override
    public void run() {
        if (isShuttingDown){
            return;
        }
        try {
            this.runTask();
        }catch (Throwable t){
            logger.error("轮询线程执行失败",t);
        }

    }

    protected void runTask() {

    }

    protected long getIntervalMs() {
        return -1;
    }
}
