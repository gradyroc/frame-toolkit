package cn.grady.enumeration;

/**
 * ClassName MDCKey
 * Description
 * Create by gradyly
 * Date 2022/4/19 23:50
 */
public enum MDCKey implements BasicEnum<String>{

    LOG_LEVEL("logLevel","日志级别"),

    ;




    private String value;
    private String description;

    MDCKey(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "MDCKey{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
