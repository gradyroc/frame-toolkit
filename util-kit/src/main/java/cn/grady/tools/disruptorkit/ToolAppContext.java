package cn.grady.tools.disruptorkit;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author grady
 * @version 1.0, on 0:17 2021/5/11.
 */
public class ToolAppContext {
    private static ConfigurableApplicationContext context;

    private static AppConfig config;

    static void set(ConfigurableApplicationContext ac) {
        context = ac;
        config = context.getBean(AppConfig.class);
    }

    public static ConfigurableApplicationContext get() {
        return context;
    }


}
