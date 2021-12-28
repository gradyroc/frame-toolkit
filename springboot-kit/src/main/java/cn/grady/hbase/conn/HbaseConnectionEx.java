package cn.grady.hbase.conn;

import cn.grady.hbase.validate.Validator;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author grady
 * @version 1.0, on 0:57 2021/12/21.
 */
public class HbaseConnectionEx extends Thread implements IConnection {

    private static final Logger log = LoggerFactory.getLogger(HbaseConnectionEx.class);
    private static final String DEFAULT_DELIMITER_OF_NAMESPACE_AND_TABLE = ":";



    private Connection connection;
    private String namespace;
    private String connName;

    public HbaseConnectionEx(Connection connection, String namespace, String connName) {
        super("hbase-connection-" + connName);
        Validator.checkNull(connection, "connection");
        Validator.checkEmpty(connName, "connName");
        Validator.checkEmpty(namespace, "namespace");
        this.connection = connection;
        this.namespace = namespace;
        this.connName = connName;
    }


    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getConnName() {
        return this.connName;
    }

    @Override
    public String getTableWithNamespace(String table) {
        Validator.checkEmpty(table, "table");
        return namespace + DEFAULT_DELIMITER_OF_NAMESPACE_AND_TABLE + table;
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public String toString() {
        return "HbaseConnectionEx{" +
                "connection=" + connection +
                ", namespace='" + namespace + '\'' +
                ", connName='" + connName + '\'' +
                '}';
    }
}
