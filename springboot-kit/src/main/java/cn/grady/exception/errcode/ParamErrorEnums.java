package cn.grady.exception.errcode;


import cn.grady.enumeration.BasicEnum;

/**
 * @author rociss
 * @version 1.0, on 22:20 2020/4/1.
 */
public enum ParamErrorEnums implements BasicEnum<String> {
    /*
    入参统一错误码
     */
    _default("REQ_0000", "parameter error"),
    PARAMETER_NOT_EMPTY("REQ_0001", "parameter showd not empty"),
    PARAMETER_EXCEDED("REQ_0002", "parameter showd not too long than the max size");

    private String code;
    private String message;

    ParamErrorEnums(String code, String message) {
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
