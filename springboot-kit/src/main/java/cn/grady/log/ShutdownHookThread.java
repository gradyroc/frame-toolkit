package cn.grady.log;

import cn.grady.BootStrap;
import org.apache.logging.log4j.LogManager;

/**
 * @author grady
 * @version 1.0, on 1:51 2021/8/25.
 */
public class ShutdownHookThread extends Thread {


    @Override
    public void run() {

        // 当容器启动完成之后，执行相关的任务
        while (BootStrap.Hook.isSystemRunning()){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        LogManager.shutdown();
    }
}
