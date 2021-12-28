package cn.grady.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author grady
 * @version 1.0, on 1:36 2021/12/29.
 */
public class ConfigDirUtil {
    public static final Logger log = LoggerFactory.getLogger(ConfigDirUtil.class);
    public static final String APP_PROPERTIES = "application.properties";
    public static final String configDir;

    static {
        URL url = ConfigDirUtil.class.getClassLoader().getResource(APP_PROPERTIES);
        if (null == url) {
            throw new RuntimeException("the application.properties must be in config path");
        } else {
            configDir = url.getFile().replaceAll(APP_PROPERTIES, "");
            log.info("the configDir is {}.", configDir);
        }
    }
}
