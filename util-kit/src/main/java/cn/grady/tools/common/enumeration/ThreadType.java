package cn.grady.tools.common.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:02 2021/5/12.
 */
public enum ThreadType implements BasicEnum<String> {

    main("main", "主线程"),
    poolTask("PoolTask", "轮询线程"),
    ;

    /**
     * 枚举值
     */
    private String value;

    /**
     * 枚举描述
     */
    private String description;

    ThreadType(String value, String description) {
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
        return String.format("%s(%s,%s)",this.name(),this.getValue(),this.getDescription());
    }
}
