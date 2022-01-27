package cn.grady.util;


import cn.grady.enumeration.BasicEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author grady
 * @version 1.0, on 23:38 2021/6/2.
 */
public class BasicEnumUtil {

    public static <E extends BasicEnum<String>> String getValue(Class<E> enumClass) {
        if (enumClass == null){
            return null;
        }
        List<String > values = Lists.newArrayList();
        for (E eval : enumClass.getEnumConstants()) {
            values.add(eval.getCode());
        }
        return StringUtils.join(values,",");
    }

    /*
     * 将字符串转换为 BasicEnum
     */
    public static <E extends BasicEnum<String>> E parse2Enum(Class<E> enumClass, String value) {
        if (value == null) {
            return null;
        }
        for (E eval : enumClass.getEnumConstants()) {
            if (value.equals(eval.getCode())) {
                return eval;
            }
        }

        return null;
    }
}
