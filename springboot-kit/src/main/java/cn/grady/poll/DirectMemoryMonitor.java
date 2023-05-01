package cn.grady.poll;

import cn.grady.util.DirectMemoryMonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author rociss
 * @version 1.0, on 1:10 2023/5/2.
 */
@Component
public class DirectMemoryMonitor extends AbstractPollThread {
    public static final Logger logger = LoggerFactory.getLogger(DirectMemoryMonitor.class);

    @Override
    protected void runTask() {

        String directMemoryINfo  = DirectMemoryMonitorUtil.getDirectMemoryInfo();
        logger.info("directMemoryInfo:{}",directMemoryINfo);
    }

    @Override
    protected long getIntervalMs() {
        return 60000;
    }
}
