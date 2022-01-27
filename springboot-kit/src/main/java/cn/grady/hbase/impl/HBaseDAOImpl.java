package cn.grady.hbase.impl;

import cn.grady.config.HBaseConfig;
import cn.grady.hbase.HBaseDAO;
import cn.grady.hbase.callback.ConnectionCallback;
import cn.grady.hbase.callback.ResultCallback;
import cn.grady.hbase.callback.ResultScannerCallback;
import cn.grady.hbase.callback.TableCallback;
import cn.grady.hbase.conn.HbaseMultiConnectionEx;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hdfs.web.JsonUtil;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 23:12 2022/1/8.
 */
public class HBaseDAOImpl implements HBaseDAO {

    public static final Logger logger = LoggerFactory.getLogger(HBaseDAOImpl.class);


    private HbaseMultiConnectionEx multiConnEx;
    private String beanName;
    private HBaseConfig hBaseConfig;

    public HBaseDAOImpl(HbaseMultiConnectionEx multiConnEx, String beanName, HBaseConfig hBaseConfig) {
        this.multiConnEx = multiConnEx;
        this.beanName = beanName;
        this.hBaseConfig = hBaseConfig;
    }


    @Override
    public <T> T execute(ConnectionCallback<T> connectionCallback) {
        return null;
    }

    @Override
    public <T> T execute(String tableName, TableCallback<T> tableCallback, Operation op) {
        return execute(connEx -> {

            long start = System.currentTimeMillis();

            String tableWithNamespace = connEx.getTableWithNamespace(tableName);
            Connection conn = connEx.getConnection();

            try (Table table = conn.getTable(TableName.valueOf(tableWithNamespace))){

                T r = tableCallback.doInTable(table);
                //TODO: 2022/1/19 print log
                return r;
            }catch (Throwable e){
                logger.error("error in execute function " ,e);
                throw e;
            }finally {

                long now = System.currentTimeMillis();
                long rtMs = now-start;
                logger.info("hbaseNode:{},Operation:{},Result:{},RtMs:{}",connEx.getConnName(),op.toJSON(), "",rtMs);
            }


        });
    }

    @Override
    public <T> T get(String tableName, Get get, ResultCallback<T> rc) {
        return execute(tableName,table -> {
            Result result = table.get(get);
            T re = rc.mapRow(result);
            return re;
        },get);
    }

    @Override
    public <T> T get(String tableName, Get get, Class<T> r) {
        return null;
    }

    @Override
    public <T> T get(String rowkey, Class<T> pr) {
        return null;
    }

    @Override
    public <T> T get(String rowkey, Filter filter, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> get(List<String> rowkeyList, Filter filter, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> get(String tableName, List<Get> gets, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> get(String tableName, List<Get> gets, ResultCallback<T> rc) {
        return null;
    }

    @Override
    public <T> T scan(String tableName, Scan scan, ResultScannerCallback<T> rsc) {
        return null;
    }

    @Override
    public <T> List<T> scan(String tableName, Scan scan, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> scan(Scan scan, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> scan(String startKey, String endKey, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> scan(String startKey, String endKey, Filter filter, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> scan(String startKey, String endKey, Filter filter, Class<T> r, boolean scanReverse) {
        return null;
    }

    @Override
    public <T> List<T> scan(String startKey, String endKey, Filter filter, int scanSize, Class<T> r) {
        return null;
    }

    @Override
    public <T> List<T> scan(String startKey, String endKey, Filter filter, int scanSize, Class<T> r, boolean scanReverse) {
        return null;
    }
}
