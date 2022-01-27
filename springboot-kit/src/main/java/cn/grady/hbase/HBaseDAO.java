package cn.grady.hbase;

import cn.grady.hbase.callback.ConnectionCallback;
import cn.grady.hbase.callback.ResultCallback;
import cn.grady.hbase.callback.ResultScannerCallback;
import cn.grady.hbase.callback.TableCallback;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Operation;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * @author grady
 * @version 1.0, on 23:10 2022/1/8.
 */
public interface HBaseDAO {

    <T> T execute(ConnectionCallback<T> connectionCallback);


    <T> T execute(String tableName, TableCallback<T> tableCallback, Operation op);

    <T>  T get(String tableName,Get get ,ResultCallback<T> rc);
    <T>  T get(String tableName,Get get ,Class<T> r);

    <T> T get(String rowkey,Class<T> r);

    <T> T get(String rowkey, Filter filter,Class<T> r);

    <T> List<T> get(List<String> rowkeyList, Filter filter, Class<T> r);

    <T> List<T> get(String  tableName, List<Get> gets, Class<T> r);

    <T> List<T> get(String  tableName, List<Get> gets, ResultCallback<T> rc);


    <T> T scan (String tableName, Scan scan , ResultScannerCallback<T> rsc);
    <T> List<T> scan (String tableName, Scan scan , Class<T> r);
    <T> List<T> scan ( Scan scan , Class<T> r);


    /**
     * 指定 start 和end 的scan
     */
    <T> List<T> scan ( String startKey ,String endKey , Class<T> r);
    <T> List<T> scan ( String startKey ,String endKey ,Filter filter, Class<T> r);
    <T> List<T> scan ( String startKey ,String endKey ,Filter filter, Class<T> r,boolean scanReverse);
    <T> List<T> scan ( String startKey ,String endKey ,Filter filter,int scanSize, Class<T> r);
    <T> List<T> scan ( String startKey ,String endKey ,Filter filter,int scanSize, Class<T> r,boolean scanReverse);


}
