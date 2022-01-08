package cn.grady.enumeration;

/**
 * @author grady
 * @version 1.0, on 1:31 2021/12/30.
 */
public enum ExceptionType implements BasicEnum<String> {

    Business("B", "business exception"),
    Technical("T", "technical exception"),
    PayLoad("C", "input parama exception"),
    Unknown("U", "unknown exception");

    private String value;
    private String description;

    ExceptionType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ExceptionType{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
