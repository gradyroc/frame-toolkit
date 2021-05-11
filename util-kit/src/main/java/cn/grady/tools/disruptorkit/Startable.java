package cn.grady.tools.disruptorkit;

/**
 * @author grady
 * @version 1.0, on 0:48 2021/5/12.
 * 自己要启动的公共接口，可以去掉tomcat不退出的容器等
 */

public interface Startable {
    void startup();

    void shutdown();

    int getOrder();
}
