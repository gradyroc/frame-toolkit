package cn.grady.tools.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author grady
 * @version 1.0, on 1:15 2021/5/17.
 */
public abstract class AbstractPoolThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPoolThread.class);

    private static boolean isShutingDown = false;

    public static void shutDown() {
        isShutingDown = true;
    }

    public static boolean isIsShutingDown() {
        return isShutingDown;
    }

    @Override
    public void run() {
        if (isShutingDown) {
            return;
        }

        try {
            this.runTask();
        } catch (Throwable t) {
            logger.error("loop thread exec failed !",t);
        }
    }

    protected abstract void runTask();

    protected abstract long getIntervalMs();

}



