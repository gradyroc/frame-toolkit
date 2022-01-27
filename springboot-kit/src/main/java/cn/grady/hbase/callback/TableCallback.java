package cn.grady.hbase.callback;

import org.apache.hadoop.hbase.client.Table;

/**
 * @author grady
 * @version 1.0, on 0:38 2022/1/11.
 */
public interface TableCallback<T> {
    T doInTable(Table table )throws Throwable;
}
