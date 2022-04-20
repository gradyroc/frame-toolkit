package cn.grady.enumeration;

/**
 * ClassName ThreadType
 * Description
 * Create by gradyly
 * Date 2022/4/18 0:08
 */
public enum ThreadType implements BasicEnum<String> {


    shutdown_hook("ShutdownHook","ShutdownHook"),
    ;

   private String value;



    private String description;

    ThreadType(String value, String description) {
        this.value=value;
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
        return "ThreadType{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
