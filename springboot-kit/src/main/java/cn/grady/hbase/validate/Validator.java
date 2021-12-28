package cn.grady.hbase.validate;

import java.util.Collection;

/**
 * @author grady
 * @version 1.0, on 0:55 2021/12/22.
 */
public class Validator {
    public static void checkNull(Object obj, String param) {
        if (null == obj) {
            throw new IllegalArgumentException(" the param " + param + " isn't null");
        }
    }

    public static void checkEmpty(String str, String param) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException(" the param " + param + " isn't empty");
        }
    }

    public static void checkEmpty(Collection<?> collection, String param) {
        if (null == collection || collection.isEmpty()) {
            throw new IllegalArgumentException(" the param " + param + " isn't null");
        }
    }
}
