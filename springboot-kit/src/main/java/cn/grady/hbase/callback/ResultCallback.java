package cn.grady.hbase.callback;

import org.apache.hadoop.hbase.client.Result;

/**
 * @author grady
 * @version 1.0, on 13:02 2022/1/9.
 */
public interface ResultCallback<T> {
    T  mapRow(Result result) throws Throwable;
}
