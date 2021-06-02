package cn.grady.tools.common.exceptions;

import cn.grady.tools.common.enumeration.InputErrCodeDefault;

/**
 * @author grady
 * @version 1.0, on 0:43 2021/6/3.
 */
public class InputException extends RuntimeException {
    private static final long serialVersionUID = -3567619696541593731L;

    private String errCode = InputErrCodeDefault._default.getValue();

    public InputException(String message) {
        super(message);
    }

    public InputException(Throwable cause) {
        super(String.format(InputErrCodeDefault._default.getDescription(), cause.getMessage(), cause));

    }

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputException(InputErrCodeDefault errCode, Object... args) {
        super(String.format(errCode.getDescription(), args));
        this.errCode = errCode.getValue();
    }

    public InputException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
