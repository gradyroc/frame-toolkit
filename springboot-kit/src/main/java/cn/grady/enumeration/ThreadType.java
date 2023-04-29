package cn.grady.enumeration;

/**
 * @author rociss
 * @version 1.0, on 0:06 2023/4/19.
 */
public enum ThreadType implements BasicEnum<String>{
    main("main","main thread"),
    Thread("Thread","normal async thread"),
    ;


    private String code;
    private String description;

    ThreadType(String code, String desc) {
        this.code = code;
        this.description = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
