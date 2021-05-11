package cn.grady.tools.disruptorkit;

import org.assertj.core.util.Lists;
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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author gradyzhou
 * @version 1.0, on 21:09 2021/5/7.
 */

@SpringBootApplication
@Configuration
@ComponentScan("cn.grady.tools")
//@EnableAspectJAutoProxy
public class ToolsBox {

    private static Logger logger = LoggerFactory.getLogger(ToolsBox.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ToolsBox.class);
        application.run(args);
//        SpringApplication.run(ToolsBox.class,args);
    }

    public static void printBeans() {
        PrintBean bean = new PrintBean();
        ToolAppContext.get().getAutowireCapableBeanFactory().autowireBean(bean);

        logger.info("print all beans of  collections Start ===============================");
        for (String beanName : bean.getBeanNamesIterator()) {
            logger.info(beanName + "====> " + AopUtils.getTargetClass(ToolAppContext.get().getBean(beanName)).getName());
        }
        logger.info("print all beans of  collections End ===============================");

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

    public void setStarables(){

    }
    @Component
    @Order(Integer.MAX_VALUE)
    public static class Hook implements SmartLifecycle, ApplicationContextAware {
        private static boolean isRunning = false;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ToolAppContext.set((ConfigurableApplicationContext) applicationContext);
        }

        @Override
        public void start() {

            printBeans();



            ToolAppContext.get().registerShutdownHook();

            isRunning = true;
        }

        @Override
        public void stop() {

        }

        @Override
        public boolean isRunning() {
            return isRunning;
        }

        @Override
        public void stop(Runnable callback) {
            logger.info("system is stoping !");

            callback.run();

            //logging the last logs
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isRunning = false;

        }
    }


}
