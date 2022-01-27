package cn.grady.config;

import cn.grady.MainConstant;
import cn.grady.hbase.conn.HbaseConnectionEx;
import cn.grady.hbase.conn.HbaseMultiConnectionEx;
import cn.grady.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ScannerCallable;
import org.apache.hadoop.hbase.ipc.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author grady
 * @version 1.0, on 2:05 2021/12/22.
 */
public abstract class HBaseConfig {

    private static final String HBASE_KERBEROS_MASTER = "kerberos.master.";
    private static final String HBASE_KERBEROS_SLAVE = "kerberos.slave.";

    private static final Logger logger = LoggerFactory.getLogger(HBaseConfig.class);

    protected Properties props;
    protected String nameSpace;
    protected String delimiter;
    protected String warmUpTables;

    protected HBaseConfig(String confFile) {
        try {
            props = PropertyFileUtil.load(confFile);
            logger.info(" load HBase configuration file success :" + confFile + "," + props.toString().replace("hbase.", "\r\nhbase."));

            nameSpace = props.getProperty(MainConstant.conf_hbase_custom_namespace[0], MainConstant.conf_hbase_custom_namespace[1]);
            delimiter = props.getProperty(MainConstant.conf_hbase_custom_delimiter[0], MainConstant.conf_hbase_custom_delimiter[1]);

        } catch (IOException e) {

        }
    }

    Connection getMasterConn() {
        Configuration config = new Configuration();
        config.set(HConstants.ZOOKEEPER_QUORUM, props.getProperty(MainConstant.conf_hbase_zookeeper_quorum_master[0], MainConstant.conf_hbase_zookeeper_quorum_master[1]));
        config.set(HConstants.ZOOKEEPER_CLIENT_PORT, props.getProperty(MainConstant.conf_hbase_zookeeper_port_master[0], MainConstant.conf_hbase_zookeeper_port_master[1]));
        config.set(HConstants.ZOOKEEPER_ZNODE_PARENT, props.getProperty(MainConstant.conf_hbase_zookeeper_znode_parent_master[0], MainConstant.conf_hbase_zookeeper_znode_parent_master[1]));
        config.set(HConstants.HBASE_CLIENT_PAUSE, props.getProperty(MainConstant.conf_hbase_client_pause[0], MainConstant.conf_hbase_client_pause[1]));
        config.set(HConstants.HBASE_CLIENT_RETRIES_NUMBER, props.getProperty(MainConstant.conf_hbase_client_retries_number[0], MainConstant.conf_hbase_client_retries_number[1]));
        config.set(HConstants.HBASE_RPC_TIMEOUT_KEY, props.getProperty(MainConstant.conf_hbase_rpc_timeout[0], MainConstant.conf_hbase_rpc_timeout[1]));
        config.set(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, props.getProperty(MainConstant.conf_hbase_client_operation_timeout[0], MainConstant.conf_hbase_client_operation_timeout[1]));
        config.set(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, props.getProperty(MainConstant.conf_hbase_client_scanner_timeout_period[0], MainConstant.conf_hbase_client_scanner_timeout_period[1]));
        config.set(ScannerCallable.LOG_SCANNER_ACTIVITY, props.getProperty(MainConstant.conf_hbase_client_log_scanner_activity[0], MainConstant.conf_hbase_client_log_scanner_activity[1]));
        config.set(HConstants.HBASE_CLIENT_SCANNER_CACHING, props.getProperty(MainConstant.conf_hbase_client_scanner_caching[0], MainConstant.conf_hbase_client_scanner_caching[1]));
        config.set(HConstants.HBASE_CLIENT_IPC_POOL_SIZE, props.getProperty(MainConstant.conf_hbase_client_ipc_pool_size[0], MainConstant.conf_hbase_client_ipc_pool_size[1]));
        config.set(RpcClient.IDLE_TIME, props.getProperty(MainConstant.conf_hbase_ipc_client_connection_midIdleTimeBeforeClose[0], MainConstant.conf_hbase_ipc_client_connection_midIdleTimeBeforeClose[1]));

        ExecutorService pool = Executors.newScheduledThreadPool(Integer.valueOf(props.getProperty(MainConstant.conf_hbase_custom_pool_size[0], MainConstant.conf_hbase_custom_pool_size[1])));

        Connection conn = null;
        try {

            conn = configKerberosIfNeedInmaster(config, pool, props);
        } catch (Exception e) {
            //TODO: 2021/12/29 异常部分统一处理先
            SysExceptionUtil.wrapThrow("connect hbase master failed ", e);
        }
        logger.info("config:{}",config);
        logger.info("connect hbase master success !");
        return conn;
    }


    Connection getSlaveConn() {
        Configuration config = new Configuration();
        config.set(HConstants.ZOOKEEPER_QUORUM, props.getProperty(MainConstant.conf_hbase_zookeeper_quorum_slave[0], MainConstant.conf_hbase_zookeeper_quorum_slave[1]));
        config.set(HConstants.ZOOKEEPER_CLIENT_PORT, props.getProperty(MainConstant.conf_hbase_zookeeper_port_slave[0], MainConstant.conf_hbase_zookeeper_port_slave[1]));
        config.set(HConstants.ZOOKEEPER_ZNODE_PARENT, props.getProperty(MainConstant.conf_hbase_zookeeper_znode_parent_slave[0], MainConstant.conf_hbase_zookeeper_znode_parent_slave[1]));
        config.set(HConstants.HBASE_CLIENT_PAUSE, props.getProperty(MainConstant.conf_hbase_client_pause[0], MainConstant.conf_hbase_client_pause[1]));
        config.set(HConstants.HBASE_CLIENT_RETRIES_NUMBER, props.getProperty(MainConstant.conf_hbase_client_retries_number[0], MainConstant.conf_hbase_client_retries_number[1]));
        config.set(HConstants.HBASE_RPC_TIMEOUT_KEY, props.getProperty(MainConstant.conf_hbase_rpc_timeout[0], MainConstant.conf_hbase_rpc_timeout[1]));
        config.set(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, props.getProperty(MainConstant.conf_hbase_client_operation_timeout[0], MainConstant.conf_hbase_client_operation_timeout[1]));
        config.set(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, props.getProperty(MainConstant.conf_hbase_client_scanner_timeout_period[0], MainConstant.conf_hbase_client_scanner_timeout_period[1]));
        config.set(ScannerCallable.LOG_SCANNER_ACTIVITY, props.getProperty(MainConstant.conf_hbase_client_log_scanner_activity[0], MainConstant.conf_hbase_client_log_scanner_activity[1]));
        config.set(HConstants.HBASE_CLIENT_SCANNER_CACHING, props.getProperty(MainConstant.conf_hbase_client_scanner_caching[0], MainConstant.conf_hbase_client_scanner_caching[1]));
        config.set(HConstants.HBASE_CLIENT_IPC_POOL_SIZE, props.getProperty(MainConstant.conf_hbase_client_ipc_pool_size[0], MainConstant.conf_hbase_client_ipc_pool_size[1]));
        config.set(RpcClient.IDLE_TIME, props.getProperty(MainConstant.conf_hbase_ipc_client_connection_midIdleTimeBeforeClose[0], MainConstant.conf_hbase_ipc_client_connection_midIdleTimeBeforeClose[1]));

        ExecutorService pool = Executors.newScheduledThreadPool(Integer.valueOf(props.getProperty(MainConstant.conf_hbase_custom_pool_size[0], MainConstant.conf_hbase_custom_pool_size[1])));

        Connection conn = null;
        try {

            conn = configKerberosIfNeedInmaster(config, pool, props);
        } catch (Exception e) {
            //TODO: 2021/12/29 异常部分统一处理先
            SysExceptionUtil.wrapThrow("connect hbase slave failed ", e);
        }
        logger.info("config:{}",config);
        logger.info("connect hbase slave success !");
        return conn;
    }

    private Connection configKerberosIfNeedInmaster(Configuration config, ExecutorService pool, Properties props) {
        return HBaseKerberosUtil.configKerberosIfNeedWithFileDir(config, pool, HBASE_KERBEROS_MASTER, props::getProperty, ConfigDirUtil.configDir);
    }

    String getMasterCoonName(Connection conn){
        return getConnName("master",conn);
    }
    String getSlaveCoonName(Connection conn){
        return getConnName("slave",conn);
    }

    private String getConnName(String prefix, Connection conn) {
        return prefix+":"+ConnectionUtil.getConnInfo(conn);
    }


    HbaseMultiConnectionEx getMultiConnEx(){
        Connection mc = getMasterConn();
        HbaseConnectionEx mcEx = new HbaseConnectionEx(mc,this.nameSpace,getMasterCoonName(mc));

        Connection sc = getMasterConn();
        HbaseConnectionEx scEx = new HbaseConnectionEx(sc, this.nameSpace, getSlaveCoonName(sc));
        return  new HbaseMultiConnectionEx(mcEx,scEx);
    }
}
