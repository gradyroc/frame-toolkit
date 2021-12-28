package cn.grady.hbase.conn;

import org.apache.hadoop.hbase.client.Connection;

/**
 * @author grady
 * @version 1.0, on 1:46 2021/12/21.
 */
public interface IConnection {

    Connection getConnection();

    String getConnName();

    String getTableWithNamespace(String table);
}
