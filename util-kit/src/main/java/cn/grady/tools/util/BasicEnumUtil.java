package cn.grady.tools.util;

import cn.grady.tools.common.enumeration.BasicEnum;

/**
 * @author grady
 * @version 1.0, on 23:38 2021/6/2.
 */
public class BasicEnumUtil {


    /*
    * 将字符串转换为 BasicEnum
     */
    public static <E extends BasicEnum<String>> E parse2Enum(Class<E> enumClass, String value) {
        if (value == null){
            return null;
        }
        for (E eval:enumClass.getEnumConstants()){
            if (value.equals(eval.getValue())){
                return eval;
            }
        }

            return null;
    }
}
