package cn.grady.hbase.callback;

import cn.grady.hbase.conn.ConnectionEx;

/**
 * @author grady
 * @version 1.0, on 2:00 2022/1/18.
 */
public interface ConnectionCallback<T> {

    T doInConnection(ConnectionEx connEx) throws  Throwable;
}
