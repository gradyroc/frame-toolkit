package cn.grady.enumerations;

import cn.grady.interfaces.BasicEnum;

/**
 * @author rociss
 * @version 1.0, on 22:26 2020/4/1.
 */
public enum BusinessErrorEnum implements BasicEnum {

    /*
    业务逻辑统一错误码
     */
    SCAN_HBASE_TIMEOUT("BUSINESS_0001","访问hbase 超时"),
    TABLE_A_HAS_NO_DATA("BUSINESS_0002","A表没有数据");

    private String code;
    private String message;

    BusinessErrorEnum(String code, String message) {
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
