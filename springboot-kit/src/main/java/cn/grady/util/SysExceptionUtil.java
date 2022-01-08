package cn.grady.util;

import cn.grady.exception.BusinessException;
import cn.grady.exception.PayloadException;
import cn.grady.exception.TechnicalException;
import cn.grady.exception.errcode.ParamaErrCode;
import cn.grady.exception.errcode.TechErrCode;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author grady
 * @version 1.0, on 1:51 2021/12/30.
 */
public class SysExceptionUtil {

    public static Throwable getRootCause(Throwable e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        return rootCause == null ? e : rootCause;
    }

    public static void wrapThrow(Throwable e) {
        if (e instanceof BusinessException) {
            throw (BusinessException) e;
        } else if (e instanceof PayloadException) {
            throw (PayloadException) e;
        } else if (e instanceof InvocationTargetException) {
            wrapThrow(((InvocationTargetException) e).getTargetException());
        } else {
            throw new TechnicalException(e);
        }
    }

    public static void wrapThrow(String message){
        throw  new TechnicalException(message);
    }
    public static void wrapThrow(String message,Throwable e ){
        throw  new TechnicalException(message,e);
    }

    public static void wrapThrow(TechErrCode errCode ,Object... args){
        throw  new TechnicalException(errCode,args);
    }

    public static void wrapThrowPayload(ParamaErrCode errCode ,Object ... args){
        throw  new PayloadException(errCode,args);
    }
}
