package cn.grady;

import cn.grady.config.AppConfig;
import cn.grady.config.ConfigInDb;
import cn.grady.log.ShutdownHook;
import cn.grady.util.Startable;
import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
import scala.App;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * @author grady
 * @version 1.0, on 1:48 2021/8/19.
 */
@SpringBootApplication
@Configuration
@ComponentScan()
@MapperScan()
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BootStrap {
    private static final Logger logger = LoggerFactory.getLogger(BootStrap.class);


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootStrap.class);
        application.run(args);
        registerShutdownHook();

    }

    public static void registerShutdownHook(){
        logger.info("Start to register ShutdownHook");
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }


    public static void printBeans(){
        PrintBean bean = new PrintBean();
        AppContext.getCtx().getAutowireCapableBeanFactory().autowireBean(bean);

        logger.info("<==========system.id:"+AppContext.getSystemId()+"===============>");

        logger.info("print bean in container:============================>");
        for (String beanName : bean.getBeanNamesIterator()) {
            logger.info(beanName+"=====>"+AopUtils.getTargetClass(AppContext.getCtx().getBean(beanName)).getName());
        }
        logger.info(" print bean in container over <============== bean Count:"+ bean.getBeanNamesIterator().size());

    }

    private static class PrintBean {
        @Autowired
        private ConfigurableListableBeanFactory factory;

        public List<String> getBeanNamesIterator(){
            List<String> list = Lists.newArrayList(factory.getBeanNamesIterator());
            Collections.sort(list);
            return list;
        }
    }

    @Component
    @Order(Integer.MAX_VALUE)
    public static class Hook implements SmartLifecycle,ApplicationContextAware{

        private static AtomicBoolean isRunning = new AtomicBoolean(false);
        private static List<Startable> startables=null;

        /**
         * 2.实现ApplicationContextAware接口
         * 重写setApplicationContext()方法，在此方法中执行额外任务。
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
            
            setStartables();
            for (Startable startable : startables) {
                startable.startup();
            }

            AppContext.getCtx().registerShutdownHook();

            //
            AppConfig appConfig = AppContext.getCtx().getBean(AppConfig.class);

            initialStaticClass();
            logger.info(String.format("SUCCESS-SIGN: start subSystem success,systemId:{}",appConfig.getSystemId()));
            isRunning.set(true);
        }

        private void initialStaticClass() {
            new ConfigInDb();
        }

        private void setStartables() {
            Map<String, Startable> map = AppContext.getCtx().getBeansOfType(Startable.class);
            Set<String> keys = map.keySet();
            List<Startable> list = new ArrayList<>();
            for (String key : keys) {
                list.add(map.get(key));
            }
            Collections.sort(list, new Comparator<Startable>() {
                @Override
                public int compare(Startable o1, Startable o2) {
                    if (o1.getOrder()==o2.getOrder()){
                        return 0;
                    }
                    return o1.getOrder()<o2.getOrder() ? -1:1;
                }
            });

            startables = list;
        }

        @Override
        public void stop() {

        }

        @Override
        public void stop(Runnable callback) {
            for (Startable startable : startables) {
                startable.shutdown();
            }

            SmartLifecycle.super.stop(callback);
            logger.info("stop the system done !!");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isRunning.set(false);
        }

        @Override
        public boolean isRunning() {
            return isRunning.get();
        }

        public static boolean isSystemRunning(){
            return isRunning.get();
        }
        @Override
        public boolean isAutoStartup(){
            return true;
        }
    }


}
