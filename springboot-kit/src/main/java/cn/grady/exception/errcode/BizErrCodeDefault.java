package cn.grady.exception.errcode;

/**
 * @author grady
 * @version 1.0, on 22:29 2022/1/7.
 */
public enum BizErrCodeDefault implements BizErrCode<String> {
    _default("000000000", "request failed:{%s}"),;

    private String value;
    private String description;

    BizErrCodeDefault(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "BizErrCodeDefault{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
