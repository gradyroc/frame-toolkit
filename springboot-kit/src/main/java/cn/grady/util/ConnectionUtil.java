package cn.grady.util;

import cn.grady.hbase.validate.Validator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;

/**
 * @author grady
 * @version 1.0, on 12:44 2022/1/8.
 */
public class ConnectionUtil {
    public static String getConnInfo(Connection connection){
        Validator.checkNull(connection,"connection");
        Configuration cfg = connection.getConfiguration();

        return cfg.get(HConstants.ZOOKEEPER_ZNODE_PARENT).replace("/","");

    }
}
