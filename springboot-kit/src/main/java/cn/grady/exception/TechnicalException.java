package cn.grady.exception;

import cn.grady.enumeration.ExceptionType;
import cn.grady.exception.errcode.TechErrCode;

/**
 * @author grady
 * @version 1.0, on 1:56 2021/12/30.
 */
public class TechnicalException extends RuntimeException {
    private static final long serialVersionUID = -7085489199707812147L;
    public static final ExceptionType error_type = ExceptionType.Technical;

    public String getErrCode() {
        return errCode;
    }

    public String errCode = TechErrCode._default.getCode();

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(TechErrCode errCode, Object... args) {
        super(String.format(errCode.getDescription(), args));
        this.errCode = errCode.getCode();
    }

    public TechnicalException(Throwable cause, String errCode) {
        super(cause);
        this.errCode = errCode;
    }

    public TechnicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errCode = errCode;
    }
}
