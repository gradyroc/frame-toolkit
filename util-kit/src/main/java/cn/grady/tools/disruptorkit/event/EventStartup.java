package cn.grady.tools.disruptorkit.event;

import cn.grady.tools.disruptorkit.Startable;

/**
 * @author grady
 * @version 1.0, on 0:50 2021/5/12.
 */
public class EventStartup implements Startable {
    @Override
    public void startup() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
