package cn.grady.log;

import cn.grady.BootStrap;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.DefaultShutdownCallbackRegistry;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;

/**
 * @author grady
 * @version 1.0, on 2:23 2021/8/19.
 * logback 实现
 * public class ShutdownHook extends ShutdownHookBase{
 */
public class ShutdownHook extends Thread {

    public ShutdownHook(){
        super();
    }

    @Override
    public void run() {
        while (BootStrap.Hook.isSystemRunning()){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        super.stop();
    }
}
