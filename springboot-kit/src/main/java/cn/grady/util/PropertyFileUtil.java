package cn.grady.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author grady
 * @version 1.0, on 16:16 2021/12/25.
 */
public class PropertyFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyFileUtil.class);

    public static Properties load(String fileName) throws IOException {
        Properties props = new Properties();
        String confPath = System.getProperty("confPath");
        InputStream is = null;
        try {
            is = new FileInputStream(confPath + File.separator + fileName);
            props.load(is);
        } catch (IOException e) {

            try {
                //再从classpath 读取
                is = PropertyFileUtil.class.getClassLoader().getResourceAsStream(fileName);
                props.load(is);
            } catch (IOException e1) {
                logger.error("***** read property file error：" + fileName, e);
                logger.error("***** read property file error：" + fileName, e1);
                throw e;
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    //ignored
                }
            }
        }
        return props;
    }
}
