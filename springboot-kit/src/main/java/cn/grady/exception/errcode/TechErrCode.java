package cn.grady.exception.errcode;


import cn.grady.enumeration.BasicEnum;

/**
 * @author rociss
 * @version 1.0, on 22:20 2020/4/1.
 */
public enum TechErrCode implements BasicEnum<String> {
    /*
    入参统一错误码
     */
    _default("T_0000", "system error[%s]"),
    asyncThreadFailed("T_0001", "异步线程执行异常"),
    maxTpxReached("T_0002", "请求[%s] 超过每个实例最大TPS限制")
    ;

    private String code;
    private String message;

    TechErrCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return message;
    }
}
