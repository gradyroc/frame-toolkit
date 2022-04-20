package cn.grady.log;

import cn.grady.BootStrap;

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
