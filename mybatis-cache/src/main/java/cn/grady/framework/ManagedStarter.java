package cn.grady.framework;

import cn.grady.enumeration.MDCKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName ManagedStarter
 * Description
 * Create by gradyly
 * Date 2022/4/19 1:32
 */
@Component
public class ManagedStarter {
    private static final Logger logger =  LoggerFactory.getLogger(ManagedStarter.class);


    @Autowired
    private List<ManagedService> services;

    public boolean startAll(){

        return  false;
    }

    public void shutdownAll() {

        shutdownBefore(null);
    }

    private void shutdownBefore(String failedService) {
        MDC.put(MDCKey.LOG_LEVEL.getValue(),"warn");

    }
}
