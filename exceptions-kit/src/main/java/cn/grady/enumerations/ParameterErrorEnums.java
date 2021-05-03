package cn.grady.enumerations;

import cn.grady.interfaces.BasicEnum;

/**
 * @author rociss
 * @version 1.0, on 22:20 2020/4/1.
 */
public enum ParameterErrorEnums implements BasicEnum {
    /*
    入参统一错误码
     */
    PARAMETER_NOT_EMPTY("REQ_0001","parameter showd not empty"),
    PARAMETER_EXCEDED("REQ_0002","parameter showd not too long than the max size");

    private String code;
    private String message;

    ParameterErrorEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String  getValue() {
        return code;
    }

    public String getDescription() {
        return message;
    }
}
