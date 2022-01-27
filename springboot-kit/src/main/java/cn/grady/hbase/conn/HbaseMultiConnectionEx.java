package cn.grady.hbase.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author grady
 * @version 1.0, on 1:29 2022/1/9.
 */
public class HbaseMultiConnectionEx {

    public static final Logger logger = LoggerFactory.getLogger(HbaseMultiConnectionEx.class);

    private volatile ConnectionEx masterConn;
    private volatile ConnectionEx slaveConn;
    private volatile ConnectionEx defaultConn;

    private static ThreadLocal<ConnectionEx> defaultConn4Thread = new ThreadLocal<>();
    private static ThreadLocal<Boolean> selfSwitch4Thread = ThreadLocal.withInitial(()-> Boolean.FALSE);


    public HbaseMultiConnectionEx(ConnectionEx masterConn, ConnectionEx slaveConn) {
        this.masterConn = masterConn;
        this.slaveConn = slaveConn;
        this.defaultConn = masterConn;
    }


    public ConnectionEx getDefaultConn(){
        return defaultConn;
    }

    /**
     * 备库链接
     * @return
     */
    public ConnectionEx getStandbyConn(){
        if (defaultConn == masterConn){
            return slaveConn;
        }else {
            return masterConn;
        }
    }

    public void switchDefault(){
        if (defaultConn == masterConn){
            defaultConn = slaveConn;
        }else {
            defaultConn = masterConn;
        }
    }

    public void switchDefault4Thread(){
        if (defaultConn4Thread.get() == masterConn){
            defaultConn4Thread.set(slaveConn);
        }else {
            defaultConn4Thread.set(masterConn);
        }
        selfSwitch4Thread.set(true);
    }

    public static void setDefaultConn4Thread(ConnectionEx connEx){
        defaultConn4Thread.set(connEx);
        selfSwitch4Thread.set(false);
    }
    public static void clear(){
        defaultConn4Thread.remove();
        selfSwitch4Thread.remove();
    }
}
