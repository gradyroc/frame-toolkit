package cn.grady;

/**
 * @author grady
 * @version 1.0, on 0:00 2021/12/26.
 */
public interface MainConstant {

    String ROOT_PACKAGE = "cn.grady";
    String[] conf_hbase_zookeeper_quorum_master = {"hbase.zookeeper.quorum.master", "127.0.0.1"};
    String[] conf_hbase_zookeeper_port_master = {"hbase.zookeeper.port.master", "2181"};
    String[] conf_hbase_zookeeper_znode_parent_master = {"hbase.zookeeper.znode.parent.master", "/"};
    String[] conf_hbase_zookeeper_quorum_slave = {"hbase.zookeeper.quorum.slave", "127.0.0.1"};
    String[] conf_hbase_zookeeper_port_slave = {"hbase.zookeeper.port.slave", "2181"};
    String[] conf_hbase_zookeeper_znode_parent_slave = {"hbase.zookeeper.znode.parent.slave", "/"};

    String[] conf_hbase_rpc_timeout = {"hbase.rpc.timeout", "5000"};
    String[] conf_hbase_client_pause = {"hbase.client.pause", "100"};
    String[] conf_hbase_client_retries_number = {"hbase.client.retries.number", "5"};
    String[] conf_hbase_client_ipc_pool_size = {"hbase.client.ipc.pool.size", "1"};
    String[] conf_hbase_client_operation_timeout = {"hbase.client.operation.timeout", "20000"};
    String[] conf_hbase_client_scanner_timeout_period = {"hbase.client.scanner.timeout.period", "25000"};
    String[] conf_hbase_client_scanner_caching = {"hbase.client.scanner.caching", "100"};
    String[] conf_hbase_client_log_scanner_activity = {"hbase.client.log.scanner.activity", "true"};
    String[] conf_hbase_ipc_client_connection_midIdleTimeBeforeClose = {"hbase.ipc.client.connection.minIdleTimeBeforeClose", "120000"};

    String[] conf_hbase_custom_pool_size = {"hbase.custom.pool.size", "100"};
    String[] conf_hbase_custom_namespace = {"hbase.custom.namespace", "test_space"};
    String[] conf_hbase_custom_delimiter = {"hbase.custom.delimiter", ","};

    String hbase_dao_name = "hbaseDAO";

}
