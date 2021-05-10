package cn.grady.tools.disruptorkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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
            ToolAppContext.get().registerShutdownHook();
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
