package cn.grady.tools.disruptorkit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author grady
 * @version 1.0, on 1:05 2021/5/11.
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        //为默认加载
})
public class AppConfig {

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    @Value("${server.port}")
    private String serverPort ;

}
