package cn.grady.exception;

import cn.grady.enumeration.ExceptionType;
import cn.grady.exception.errcode.ParamErrorEnums;
import cn.grady.exception.errcode.ParamaErrCode;

/**
 * @author grady
 * @version 1.0, on 23:36 2021/12/29.
 */
public class PayloadException extends RuntimeException {

    private static final long serialVersionUID = 5980103450245210033L;

    private static final ExceptionType error_type = ExceptionType.PayLoad;

    public String errCode = ParamErrorEnums._default.getCode();

    public String getErrCode() {
        return errCode;
    }

    public PayloadException(String message) {
        super(message);

    }
    public PayloadException(Throwable cause) {
        super(String.format(ParamErrorEnums._default.getDescription(), cause.getMessage()), cause);

    }

    public PayloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayloadException(ParamErrorEnums errCode, Object... args) {
        super(String.format(errCode.getDescription(), args));
        this.errCode = errCode.getCode();
    }

    public PayloadException(ParamaErrCode<String> paramaErrCode, Object... args) {
        super(String.format(paramaErrCode.getDescription(), args));
        this.errCode = paramaErrCode.getCode();
    }

    public PayloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errCode = errCode;
    }

    public PayloadException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }
}
