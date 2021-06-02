package cn.grady.tools.common.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:02 2021/6/3.
 */
public enum InputErrCodeDefault implements BasicEnum {
    _default("000000", "入参错误:[%s]"),;



    private String value;
    private String desc;

    InputErrCodeDefault(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String  getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return desc;
    }
    @Override
    public String toString() {
        return String.format("%s(%s,%s)", this.name(), this.value, this.desc);
    }
}
