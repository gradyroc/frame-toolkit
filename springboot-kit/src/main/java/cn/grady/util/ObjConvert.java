package cn.grady.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author grady
 * @version 1.0, on 1:48 2022/1/22.
 */
public class ObjConvert {


    public static Date toDate(Object value) {
        return  toDate(value,DateUtil.DATE_ONLY_FORMAT);
    }

    /**
     * 按指定格式转换日期
     *
     * @param value   待转换对象
     * @param pattern 日期格式
     * @return <ul>
     * <li><tt>null</tt> 如果对象为null，或空字符串，</li>
     * <li><tt>原值</tt> 如果日期对象</li>
     * <li><tt>日期值</tt> 如果日历对象</li>
     * <li><tt>按照格式转换后的日期</tt> 如果是字符串对象</li>
     * </ul>
     * @throws java.lang.IllegalArgumentException
     *          如果是除日期，日历，字符串以外的对象，或者是字符串对象，但是转换失败
     */
    public static Date toDate(Object value, String pattern) {
        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            return (Date) value;
        } else if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.trim().length() == 0) {
                return null;
            }
            try {

                DateFormat _formater = new SimpleDateFormat(pattern);
                Date _date =  _formater.parse((String) value);
                if (strVal.equals(_formater.format(_date))) {
                    return _date;
                } else {
                    throw new java.lang.IllegalArgumentException("模式：[" + pattern + "]与时间串:[" + value + "]不符");
                }
            } catch (ParseException e) {
                throw new java.lang.IllegalArgumentException("不能使用模式：[" + pattern + "]格式化时间串:[" + value + "]");

            }
        }else {
            throw new java.lang.IllegalArgumentException("不能使用模式：[" + pattern + "]格式化未知对象:[" + value.getClass().getName()+ "]");
        }
    }
}
