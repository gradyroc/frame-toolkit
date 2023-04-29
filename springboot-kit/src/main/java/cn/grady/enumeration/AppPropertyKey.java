package cn.grady.enumeration;

/**
 * @author rociss
 * @version 1.0, on 0:42 2023/4/19.
 */
public enum AppPropertyKey implements BasicEnum<String >{

    CONFIG_IN_DB_REFRESH_INTERVAL("config.in.db.refresh.interval.ms","config in db refresh interval "),

    ;



    private String code;
    private String description;

    AppPropertyKey(String code, String desc) {
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

    @Override
    public String toString() {
        return "AppPropertyKey{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
