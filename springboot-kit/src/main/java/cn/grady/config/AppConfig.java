package cn.grady.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 加载配置文件，以及基本属性的获取
 *
 * @author grady
 * @version 1.0, on 0:05 2021/8/23.
 */

@Configuration
@PropertySources({
        /**
         * application.properties或者 application.properties 为默认加载
         */
})
public class AppConfig {
    @Configuration
    @Profile("default")
    @PropertySources({
            @PropertySource("classpath:addl.properties"),
                    })
    static class EnvironmentConfig{

    }

    @Value("${system.id}")
    private String systemId;

    @Value("${system.mode}")
    private String systemMode;

    public String getSystemId() {
        return systemId;
    }

    public String getSystemMode() {
        return systemMode;
    }

}
