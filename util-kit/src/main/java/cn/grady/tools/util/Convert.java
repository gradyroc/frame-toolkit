package cn.grady.tools.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author grady
 * @version 1.0, on 2:07 2021/6/3.
 */
public class Convert {

    /**
     * 按指定格式转换为日期
     *
     * @param value   待转换的对象
     * @param pattern 日期格式
     * @return 转换后的日期对象
     * <ul>
     * <li><tt>null</tt> 如果对象为null，或对象为空字符串，或对象为全空格字符串.
     * <li><tt>原值</tt> 如果是日期对象.
     * <li><tt>日期值</tt> 如果是日历对象.
     * <li><tt>按格式转换后的日期</tt> 如果是字符串对象.
     * </ul>
     * @throws java.lang.IllegalArgumentException 如果是除日期、日历、字符串以外的对象,或者是字符串对象,但转换失败.
     */
    public static Date toDate(Object value, String pattern) throws java.lang.IllegalArgumentException {
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
                Date _date = _formater.parse((String) value);
                if (strVal.equals(_formater.format(_date))) {
                    return _date;
                } else {
                    throw new IllegalArgumentException("模式:[" + pattern + "]与时间串:[" + value + "]不符");
                }
            } catch (Exception e) {
                throw new java.lang.IllegalArgumentException("不能使用模式:[" + pattern + "]格式化时间串:[" + value + "]");
            }
        } else {
            throw new java.lang.IllegalArgumentException("不能使用模式:[" + pattern + "]格式化未知对象:[" + value + "]" + value.getClass().getName());
        }
    }

    /**
     * 转换为日期(出错时使用缺省值)
     *
     * @param value      待转换的对象
     * @param pattern    日期格式
     * @param defaultVal 缺省值
     * @return 转换后的日期对象
     * <ul>
     * <li><tt>缺省值</tt> 如果对象为null，或对象为空字符串，或对象为全空格字符串，或转换失败，或非日历、日期、字符串对象.
     * <li><tt>原值</tt> 如果是日期对象.
     * <li><tt>日期值</tt> 如果是日历对象.
     * <li><tt>按格式转换后的日期</tt> 如果是字符串对象.
     * </ul>
     */
    public static Date toDate(Object value, String pattern, Date defaultVal) {
        Date ret = null;
        try {
            ret = toDate(value, pattern);
        } catch (java.lang.IllegalArgumentException e) {
            ret = defaultVal;
        }
        return (ret == null ? defaultVal : ret);
    }

    /**
     * 转换为日期（使用缺省格式：yyyyMMdd）. 相当于:toDate(value, "yyyyMMdd", null)
     *
     * @param value 待转换的对象
     * @return 转换后的日期对象
     * <ul>
     * <li><tt>null</tt> 如果对象为null，或对象为空字符串，或对象为全空格字符串，或转换失败，或非日历、日期、字符串对象.
     * <li><tt>原值</tt> 如果是日期对象.
     * <li><tt>日期值</tt> 如果是日历对象.
     * <li><tt>按格式转换后的日期</tt> 如果是字符串对象.
     * </ul>
     */
    public static Date toDate(Object value) {
        return toDate(value, DateUtil.DATE_ONLY_FORMAT, null);
    }

}
