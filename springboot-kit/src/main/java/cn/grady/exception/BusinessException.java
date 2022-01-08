package cn.grady.exception;

import cn.grady.enumeration.ExceptionType;
import cn.grady.exception.errcode.BizErrCode;
import cn.grady.exception.errcode.BizErrCodeDefault;

/**
 * @author grady
 * @version 1.0, on 22:25 2022/1/7.
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -3590301267686857261L;
    public static final ExceptionType error_type = ExceptionType.Business;

    private String errCode = BizErrCodeDefault._default.getValue();

    public String getErrCode() {
        return errCode;
    }


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public BusinessException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(Throwable cause, String errCode) {
        super(cause);
        this.errCode = errCode;
    }
    public BusinessException(Throwable cause, BizErrCode<String > errCode) {
        super(cause);
        this.errCode = errCode.getValue();
    }

    public BusinessException(Throwable cause, BizErrCode<String > errCode,Object... args){
        super(String.format(errCode.getDescription(),args),cause);
        this.errCode = errCode.getValue();
    }
}
