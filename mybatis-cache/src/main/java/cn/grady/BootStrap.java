package cn.grady;

import cn.grady.enumeration.ThreadType;
import cn.grady.framework.ManagedStarter;
import cn.grady.log.ShutdownHook;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName AppConfig
 * Description
 * Create by gradyly
 * Date 2022/4/18 0:00
 */

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class BootStrap {

    private static final Logger logger = LoggerFactory.getLogger(BootStrap.class);


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootStrap.class);
        application.run(args);

        registerShutdownHook();

    }

    public static void registerShutdownHook() {
        logger.info("Start to register ShutdownHook");
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }


    public static void printBeans() {
        PrintBean bean = new PrintBean();
        AppContext.getCtx().getAutowireCapableBeanFactory().autowireBean(bean);

        logger.info("<==========system.id:" + AppContext.getSystemId() + "===============>");

        logger.info("print bean in container:============================>");
        for (String beanName : bean.getBeanNamesIterator()) {
            logger.info(beanName + "=====>" + AopUtils.getTargetClass(AppContext.getCtx().getBean(beanName)).getName());
        }
        logger.info(" print bean in container over <============== bean Count:" + bean.getBeanNamesIterator().size());

    }

    private static class PrintBean {
        @Autowired
        private ConfigurableListableBeanFactory factory;

        public List<String> getBeanNamesIterator() {
            List<String> list = Lists.newArrayList(factory.getBeanNamesIterator());
            Collections.sort(list);
            return list;
        }
    }

    @Component
    @Order(Integer.MAX_VALUE)
    public static class Hook implements SmartLifecycle, ApplicationContextAware {

        private static AtomicBoolean isRunning = new AtomicBoolean(false);

        /**
         * 2.实现ApplicationContextAware接口
         * 重写setApplicationContext()方法，在此方法中执行额外任务。
         *
         * @param applicationContext
         * @throws BeansException
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            AppContext.set((ConfigurableApplicationContext) applicationContext);
        }

        @Override
        public void start() {
            logger.info("print beans in container");
            printBeans();
            boolean started = AppContext.getCtx().getBean(ManagedStarter.class).startAll();
            if (started){
                logger.info(String.format("success-sign:system start successfull,systemID=%s","grady-test"));

                isRunning.set(true);
            }else {
                System.exit(1);
            }

        }

        @Override
        public void stop() {
            logger.info("stop the system");
            stop(null);
        }

        @Override
        public boolean isRunning() {
            return isRunning.get();
        }

        public static boolean isSystemRunning() {
            return isRunning.get();
        }

        @Override
        public boolean isAutoStartup() {
            return true;
        }

        @Override
        public void stop(Runnable callback) {
            Thread.currentThread().setName(ThreadType.shutdown_hook.getValue());
            logger.warn("begin to stop the system");
            AppContext.getCtx().getBean(ManagedStarter.class).shutdownAll();

            if (callback != null){
                callback.run();
            }
            logger.warn("sucessed  stoped  the system");
            isRunning.set(false);
        }
    }

}
