package cn.grady.hbase.callback;

import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * @author grady
 * @version 1.0, on 0:26 2022/1/11.
 */
public interface ResultScannerCallback<T> {
    T extractData(ResultScanner rs) throws  Throwable;
}
