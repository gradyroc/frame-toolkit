package cn.grady.util;

/**
 * @author rociss
 * @version 1.0, on 21:50 2023/4/15.
 */
public interface Startable {
    void startup();
    void shutdown();
    int getOrder();
}
