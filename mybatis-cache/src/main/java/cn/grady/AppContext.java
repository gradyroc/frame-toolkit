package cn.grady;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 保存context 句柄，使得不经容器初始化的对象也可以访问容器内容
 * @author grady
 * @version 1.0, on 0:04 2021/8/23.
 *
 */
public class AppContext {


    private static ConfigurableApplicationContext ctx;
    private static AppConfig appConfig;

    static void set(ConfigurableApplicationContext ac){
        ctx=ac;
        appConfig = ctx.getBean(AppConfig.class);
    }


    /**
     * 非容器创建的对象，想要获得ApplicationContext句柄，从而访问容器内容
     * @return
     */
    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    public static String getSystemId(){
        return appConfig.getSystemId();
    }
}
