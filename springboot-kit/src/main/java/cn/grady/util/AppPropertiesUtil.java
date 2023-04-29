package cn.grady.util;

import cn.grady.enumeration.AppPropertyKey;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author rociss
 * @version 1.0, on 0:32 2023/4/19.
 */
public class AppPropertiesUtil {
    public static final String APPLICATION_PROPERTIES ="application.properties";
    public static Properties applicationPro;

    static {
        try {
            applicationPro = PropertyFileUtil.load(APPLICATION_PROPERTIES);
        }catch (IOException e){
            Throwables.throwIfUnchecked(e);
        }
    }

    public static int getInt(AppPropertyKey key){
        return Integer.parseInt(applicationPro.getProperty(key.getCode()));
    }
    public static String getString(AppPropertyKey key ){
        return applicationPro.getProperty(key.getCode());
    }

    public static String getString(AppPropertyKey key ,String param,String defaultVal){
        String val = applicationPro.getProperty(key.getCode() + param);
        if (StringUtils.isEmpty(val)){
            return defaultVal;
        }else {
            return val;
        }
    }
}
