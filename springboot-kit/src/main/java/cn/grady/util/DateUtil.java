package cn.grady.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.commons.lang3.StringUtils;

//import com.webank.dpqs.framework.enumeration.DateFrq;

/**
 * 日期实用类 从老系统拷贝过来的
 *
 * @author
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String DATE_ONLY_FORMAT = "yyyyMMdd";
    public static final String DATE_ONLY_FORMAT_6BIT = "yyMMdd";

    public static final String MONTH_ONLY_FORMAT = "yyyyMM";

    public static final String YEAR_ONLY_FORMAT = "yyyy";

    public static final String TIMESTAMP_WITH_MS_DB = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String TIME_FORMAT_LONG_WITH_MS = "yyyyMMdd HH:mm:ss.SSS";
    public static final String TIME_FORMAT_LONG = "yyyyMMdd HH:mm:ss";
    public static final String TIME_FORMAT_LONG_17 = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_WITHOUT_SEMICOLON = "HHmmss";
    public static final String TIME_FORMAT_WITHOUT_SEMICOLON_MS = "HHmmssSSS";

    public static final String TIME_FORMAT_WITH_MS = "HH:mm:ss.SSS";

    public static final String DEFAULT_DATE = "20991231";

    public static final String TIMESTAMP_WITH_MS_APP = "yyyyMMdd HHmmssSSS";

    /**
     * Yong
     *
     * @param date
     * @param parttern
     * @return
     */
    public static boolean isValid(String date, String parttern) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(parttern)) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(parttern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * valid time with format HH:mm:ss
     *
     * @param date
     * @return
     */
    public static boolean isValidTime(String date) {
        return isValid(date, DateUtil.TIME_FORMAT);
    }

    /**
     * 将java.util.Date对象转换为java.sql.Timestamp对象
     *
     * @param value
     * @return java.sql.Timestamp。
     */
    public static Timestamp getTimestamp(Date value) {
        if (value == null) {
            return null;
        }
        return new Timestamp(value.getTime());
    }

    /**
     * 取得当前时间，返回Timestamp对象
     *
     * @param
     * @return Timestamp。
     */
    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 根据给定格式，返回字符串格式的当前时间
     *
     * @param format 给定格式，若给定格式为空，则格式默认为"yyyy-MM-dd HH:mm:ss"
     * @return String。
     */
    public static String getNow(String format) {
        if (null == format || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return formatDate(new Date(), format);
    }

    /**
     * 截取日期部分
     * <p>
     * 如sDate为null,则返回当前日期
     *
     * @param sDate
     * @return
     */
    public static Date getDate(Date sDate) {
        Date tDate = sDate == null ? new Date() : sDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 取得某月天数
     *
     * @param year  int 年（例2004）
     * @param month int 月（1-12）
     * @return int 当月天数
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * @param year
     * @return
     */
    public static int getDaysOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * @param sDate 字符串日期
     * @return Date 日期类型
     * @throws ParseException
     * @Author T
     * <p>
     * <li>2014年3月11日-下午5:10:31</li>
     * <li>功能说明：将字符串日期转为日期类型</li>
     * </p>
     */
    public static Date covStringToDate(String sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            return dateFormat.parse(sDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date covStringToDate(String sDate, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(sDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param sDate 字符串日期
     * @return Date 日期类型
     * @throws ParseException
     * @Author T
     * <p>
     * <li>2014年3月11日-下午5:10:31</li>
     * <li>功能说明：将字符串日期转为日期类型</li>
     * </p>
     */
    public static Date covMillsStringToDate(String sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        try {
            return dateFormat.parse(sDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不合法:" + sDate);
        }
    }

    /**
     * @param dtDate 日期类型
     * @return 字符串日期
     * @Author T
     * <p>
     * <li>2014年3月11日-下午5:20:25</li>
     * <li>功能说明：将日期类型转为字符串日期</li>
     * </p>
     */
    public static String covDateToString(Date dtDate) {
        Format f = new SimpleDateFormat("yyyyMMdd");

        return f.format(dtDate);
    }

    /**
     * 使用中文字符以复杂的形式（"年 月 日 上午 时 分 秒"）格式化时间串
     *
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String complexFormatChineseDate(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        String timeStr = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        timeStr = timeStr + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分"
                + calendar.get(Calendar.SECOND) + "秒";
        calendar.clear();

        return timeStr;
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    public static String getDate() {
        return DateUtil.formatDate(new Date(), DATE_ONLY_FORMAT);
    }

    /**
     * yyMMdd
     *
     * @return
     */
    public static String getDate6Bit() {
        return DateUtil.formatDate(new Date(), DATE_ONLY_FORMAT_6BIT);
    }

    /**
     * HHmmss
     *
     * @return
     */
    public static String getTime() {
        return DateUtil.formatDate(new Date(), TIME_FORMAT_WITHOUT_SEMICOLON);
    }

    public static String getTime(Date date, String parttern) {
        return DateUtil.formatDate(date, parttern);
    }

    /**
     * HH:mm:ss
     *
     * @return
     */
    public static String getTimeNormal() {
        return DateUtil.formatDate(new Date(), TIME_FORMAT);
    }

    /**
     * HH:mm:ss.SSS
     *
     * @return
     */
    public static String getTimeWithMs() {
        return DateUtil.formatDate(new Date(), TIME_FORMAT_WITH_MS);
    }

    /**
     * yyyyMMdd HH:mm:ss.SSS
     *
     * @return
     */
    public static String getDateTimeWithMs() {
        return DateUtil.formatDate(new Date(), TIME_FORMAT_LONG_WITH_MS);
    }

    /**
     * yyyyMMdd HH:mm:ss
     *
     * @return
     */
    public static String getDateTime() {
        return DateUtil.formatDate(new Date(), TIME_FORMAT_LONG);
    }

    /**
     * 使用格式 <b>_pattern </b>格式化日期输出
     *
     * @param _date    日期对象
     * @param _pattern 日期格式
     * @return 格式化后的日期
     */
    public static String formatDate(Date _date, String _pattern) {
        if (_date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_pattern);
        String stringDate = simpleDateFormat.format(_date);

        return stringDate;
    }

    public static boolean chkIsDate(String sDate) {
        boolean bValid = false;
        if (StringUtils.isEmpty(sDate)) {
            return false;
        }
        Format f = new SimpleDateFormat("yyyyMMdd");
        String tmp;
        try {
            tmp = f.format(covStringToDate(sDate));
        } catch (Exception e) {
            return false;
        }
        bValid = Objects.equals(tmp, sDate);

        return bValid;
    }

    /**
     * 使用格式 {@link #DATE_ONLY_FORMAT}格式化日期输出
     *
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String formatDate(Date _date) {
        return formatDate(_date, DATE_ONLY_FORMAT);
    }

    /**
     * 获得以参数_fromDate为基数的年龄
     *
     * @param _birthday 生日
     * @param _fromDate 起算时间
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Date _birthday, Date _fromDate) {

        if (_fromDate == null) {
            _fromDate = new Date(System.currentTimeMillis());
        }

        Calendar calendar = getCalendarInstance();
        calendar.setTime(_birthday);

        int birthdayYear = calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH);
        int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.setTime(_fromDate);

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();

        int age = currentYear - birthdayYear;

        if (!((currentMonth >= birthdayMonth) && (currentDay >= birthdayDay))) {
            age--;
        }

        return age;
    }

    /**
     * 获得当前年龄
     *
     * @param _birthday 生日
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Date _birthday) {
        return getAgeFromBirthday(_birthday, new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前年龄
     *
     * @param _birthday 生日
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Timestamp _birthday) {
        return getAgeFromBirthday(new Date(_birthday.getTime()),
                new Date(System.currentTimeMillis()));
    }

    /**
     * 获得日期的天，以月为基
     *
     * @param _date 日期对象
     * @return 日期的天
     */
    public static int getDay(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();

        return day;
    }

    /**
     * 计算两个日期间相隔的天数
     *
     * @param _startDate 起始日期
     * @param _endDate   终止日期
     * @return 相隔天数, 如果结果为正表示 <b>_endDate </b>在 <b>_startDate </b>之后；如果结果为负表示
     * <b>_endDate </b>在 <b>_startDate </b>之前；如果结果为0表示 <b>_endDate </b>和
     * <b>_startDate </b>是同一天。
     */
    public static int getDayCount(Date _startDate, Date _endDate) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_startDate);

        int startDay = calendar.get(Calendar.DAY_OF_YEAR);
        int startYear = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.setTime(_endDate);

        int endDay = calendar.get(Calendar.DAY_OF_YEAR);
        int endYear = calendar.get(Calendar.YEAR);
        calendar.clear();

        return ((endYear - startYear) * 365) + (endDay - startDay);
    }

    public static int getDayCount(String _startDate, String sdf, String _endDate, String edf) {
        return getDayCount(ObjConvert.toDate(_startDate, sdf), ObjConvert.toDate(_endDate, edf));
    }

    public static int getDayCount(String _startDate, String _endDate) {
        return getDayCount(ObjConvert.toDate(_startDate), ObjConvert.toDate(_endDate));
    }

    /**
     * 获得日期的小时
     *
     * @param _date 日期对象
     * @return 日期的小时
     */
    public static int getHours(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.HOUR);
        calendar.clear();

        return value;
    }

    /**
     * 获得日期的分钟
     *
     * @param _date 日期对象
     * @return 日期的分钟
     */
    public static int getMinutes(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.MINUTE);
        calendar.clear();

        return value;
    }

    /**
     * 获得日期的月
     *
     * @param _date 日期对象
     * @return 日期的月
     */
    public static int getMonth(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        // 以0开始
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();

        return (month + 1);
    }

    /**
     * 计算两个日期间相隔的月数, 每隔一月，其相隔天数必>30
     *
     * @param _startDate 起始日期
     * @param _endDate   终止日期
     * @return 相隔月数, 如果结果为正表示 <b>_endDate </b>在 <b>_startDate </b>之后；如果结果为负表示
     * <b>_endDate </b>在 <b>_startDate </b>之前；如果结果为0表示 <b>_endDate </b>和
     * <b>_startDate </b>是同一天。
     */
    public static int getMonthCount(Date _startDate, Date _endDate) {
        Date startDate = _startDate;
        Date endDate = _endDate;
        boolean afterFlag = false;

        if (_startDate.after(_endDate)) {
            startDate = _endDate;
            endDate = _startDate;
            afterFlag = true;
        }

        Calendar calendar = getCalendarInstance();
        calendar.setTime(startDate);

        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        int startMonth = calendar.get(Calendar.MONTH);
        int startYear = calendar.get(Calendar.YEAR);
        int countDayOfStartMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.setTime(endDate);

        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        int endMonth = calendar.get(Calendar.MONTH);
        int endYear = calendar.get(Calendar.YEAR);
        calendar.clear();

        int result = ((endYear - startYear) * 12) + (endMonth - (startMonth + 1))
                + (endDay + (countDayOfStartMonth - startDay)) / countDayOfStartMonth;

        if (afterFlag) {
            return -result;
        } else {
            return result;
        }
    }

    /**
     * 获得日期的小秒
     *
     * @param _date 日期对象
     * @return 日期的秒
     */
    public static int getSeconds(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.SECOND);
        calendar.clear();

        return value;
    }

    /**
     * 获得日期的年
     *
     * @param _date 日期对象
     * @return 日期的年
     */
    public static int getYear(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        int year = calendar.get(Calendar.YEAR);
        calendar.clear();

        return year;
    }

    /**
     * 使用中文字符以简单的形式（"年 月 日"）格式化时间串
     *
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String simpleFormatChineseDate(Date _date) {
        Calendar calendar = getCalendarInstance();
        calendar.setTime(_date);

        String timeStr = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        calendar.clear();

        return timeStr;
    }

    /**
     * <p>
     * Title :雅普兰Web开发框架
     * </p>
     * <p>
     * Description: 得到当前时间（Timestamp）
     * </p>
     */
    public static Timestamp getCurTime() {
        Timestamp sdate = new Timestamp(System.currentTimeMillis());

        return sdate;
    }

    /**
     * 取期末日期
     *
     * @param thedate
     * @param period  <ul>
     *                <li>M 月末
     *                <li>Q 季末
     *                <li>Y 年末
     *                <li>H 半年末
     *                <li>T 旬末
     *                <li>m 上月末
     *                <li>q 上季末
     *                <li>y 上年末
     *                <li>h 上半年末
     *                <li>t 上一旬末
     *                </ul>
     * @return
     */
    public static Date getPeriodEnd(Date thedate, char period) {
        Calendar cal = getCalendarInstance();
        cal.setTime(thedate);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);// 1月=0,12月=11
        // int week = cal.get(Calendar.DAY_OF_WEEK);
        switch (period) {
            case 'M':
                // cal.add(Calendar.MONTH, 1);// to next month
                // day = cal.get(Calendar.DAY_OF_MONTH);
                // cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                int monthendday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(Calendar.DATE, monthendday);
                break;
            case 'Q':
                cal.add(Calendar.MONTH, 3 - month % 3);// to next quarter
                // first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'Y':
                cal.add(Calendar.MONTH, 12 - month);// to next year first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'H':
                cal.add(Calendar.MONTH, 6 - (month) % 6);// to next halfyear
                // first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'T':
                if (day < 11) {
                    cal.set(Calendar.DATE, 10);
                } else if (day < 21) {
                    cal.set(Calendar.DATE, 20);
                } else {
                    cal.add(Calendar.MONTH, 1);// to next month
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                }
                break;
            case 'm':
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'q':
                cal.add(Calendar.MONTH, 0 - (month) % 3);// to this quarter
                // first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'y':
                cal.add(Calendar.MONTH, 0 - month);// to this year first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 'h':
                cal.add(Calendar.MONTH, 0 - (month) % 6);// to this halfyear
                // first month
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                break;
            case 't':
                if (day < 11) {
                    cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
                } else if (day < 21) {
                    cal.set(Calendar.DATE, 10);
                } else {
                    cal.set(Calendar.DATE, 20);
                }
                break;
            default:
                break;
        }
        return cal.getTime();
    }

    /**
     * 取期末日期
     *
     * @param thedate
     * @param pattern
     * @param period  <ul>
     *                <li>M 月末
     *                <li>Q 季末
     *                <li>Y 年末
     *                <li>H 半年末
     *                <li>T 旬末
     *                <li>m 上月末
     *                <li>q 上季末
     *                <li>y 上年末
     *                <li>h 上半年末
     *                <li>t 上一旬末
     *                </ul>
     * @return
     */
    public static String getPeriodEnd(String thedate, String pattern, char period) {
        if (DateUtil.DATE_ONLY_FORMAT.equals(pattern)) {
            switch (period) {
                case 'Y':
                    return thedate.substring(0, 4) + "0101";
            }
        }
        Date tdate = ObjConvert.toDate(thedate, pattern);
        tdate = getPeriodEnd(tdate, period);
        return formatDate(tdate, pattern);
    }

    /**
     * 取期初日期
     *
     * @param thedate
     * @param period  <ul>
     *                <li>M 月初
     *                <li>Q 季初
     *                <li>Y 年初
     *                <li>H 半年初
     *                <li>T 旬初
     *                <li>m 上月初
     *                <li>q 上季初
     *                <li>y 上年初
     *                <li>h 上一半年初
     *                <li>t 上一旬初
     *                </ul>
     * @return
     */
    public static Date getPeriodBegin(Date thedate, char period) {
        Calendar cal = getCalendarInstance();
        cal.setTime(thedate);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);// 1月=0,12月=11
        // int week = cal.get(Calendar.DAY_OF_WEEK);
        switch (period) {
            case 'M':
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'Q':
                cal.add(Calendar.MONTH, 0 - (month - 1) % 3);// 到季初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'Y':
                cal.add(Calendar.MONTH, 1 - month);// 到年初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'H':
                cal.add(Calendar.MONTH, 0 - (month - 1) % 6);// 到半年初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'T':
                if (day < 11) {
                    cal.set(Calendar.DATE, 1);
                } else if (day < 21) {
                    cal.set(Calendar.DATE, 11);
                } else {
                    cal.set(Calendar.DATE, 21);
                }
                break;
            case 'm':
                cal.add(Calendar.MONTH, -1);// 到上月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'q':
                cal.add(Calendar.MONTH, -3 - (month - 1) % 3);// 到上季初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'y':
                cal.add(Calendar.MONTH, 1 - 12 - month);// 到上年初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 'h':
                cal.add(Calendar.MONTH, -6 - (month - 1) % 6);// 到上一半年初月份
                day = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, 1 - day);// 到月初
                break;
            case 't':
                if (day < 11) {
                    cal.add(Calendar.MONTH, -1);
                    cal.set(Calendar.DATE, 21);
                } else if (day < 21) {
                    cal.set(Calendar.DATE, 1);
                } else {
                    cal.set(Calendar.DATE, 11);
                }
                break;
            default:
                break;
        }
        return cal.getTime();
    }

    /**
     * 取期初日期
     *
     * @param thedate
     * @param pattern
     * @param period  <ul>
     *                <li>M 月初
     *                <li>Q 季初
     *                <li>Y 年初
     *                <li>H 半年初
     *                <li>T 旬初
     *                <li>m 上月初
     *                <li>q 上季初
     *                <li>y 上年初
     *                <li>h 上一半年初
     *                <li>t 上一旬初
     *                </ul>
     * @return
     */
    public static String getPeriodBegin(String thedate, String pattern, char period) {
        if (DateUtil.DATE_ONLY_FORMAT.equals(pattern)) {
            switch (period) {
                case 'M':
                    return thedate.substring(0, 6) + "01";
                case 'Y':
                    return thedate.substring(0, 4) + "0101";
            }
        }
        Date tdate = ObjConvert.toDate(thedate, pattern);
        tdate = getPeriodBegin(tdate, period);
        return formatDate(tdate, pattern);
    }

    /**
     * Description: 根据Timestamp得到Time
     *
     * @param _timesmp
     * @return
     */
    public static java.sql.Time getTimeByTimestamp(Timestamp _timesmp) {
        Calendar cal = getCalendarInstance();
        cal.setTimeInMillis(_timesmp.getTime());
        String str = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE))
                + ":" + String.valueOf(cal.get(Calendar.SECOND));

        return java.sql.Time.valueOf(str);
    }

    public static Calendar getCalendarInstance() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.SIMPLIFIED_CHINESE);
        return cal;

    }

    /**
     * 按精度比较日期. <br>
     *
     * @param date1
     * @param date2
     * @param precision 精度=整数： <br>
     *                  Calendar.YEAR <br>
     *                  Calendar.MONTH <br>
     *                  Calendar.DAY_OF_MONTH <br>
     *                  Calendar.MINUTE <br>
     *                  Calendar.SECOND <br>
     *                  Calendar.MILLISECOND
     * @return the value 0 if the argument Date is equal to this Date; a value less
     * than 0 if this Date is before the Date argument; and a value greater
     * than 0 if this Date is after the Date argument
     */
    public static int compareDate(final Date date1, final Date date2, int precision) {
        Calendar c = Calendar.getInstance();

        List<Integer> fields = new ArrayList<Integer>();
        fields.add(new Integer(Calendar.YEAR));
        fields.add(new Integer(Calendar.MONTH));
        fields.add(new Integer(Calendar.DAY_OF_MONTH));
        fields.add(new Integer(Calendar.MINUTE));
        fields.add(new Integer(Calendar.SECOND));
        fields.add(new Integer(Calendar.MILLISECOND));

        Date d1 = date1;
        Date d2 = date2;
        if (fields.contains(new Integer(precision))) {
            c.setTime(date1);
            for (int i = 0; i < fields.size(); i++) {
                int field = ((Integer) fields.get(i)).intValue();
                if (field > precision) {
                    c.set(field, 0);
                }
            }
            d1 = c.getTime();
            c.setTime(date2);
            for (int i = 0; i < fields.size(); i++) {
                int field = ((Integer) fields.get(i)).intValue();
                if (field > precision) {
                    c.set(field, 0);
                }
            }
            d2 = c.getTime();
        }
        return d1.compareTo(d2);
    }

    public static int compareDate(final String date1, String d1df, final String date2, String d2df, int precision) {
        return compareDate(ObjConvert.toDate(date1, d1df), ObjConvert.toDate(date2, d2df), precision);
    }

    public static int compareDate(final String date1, final String date2, int precision) {
        return compareDate(ObjConvert.toDate(date1), ObjConvert.toDate(date2), precision);
    }

    public static int compareDate(final String date1, final String date2) {
        return compareDate(ObjConvert.toDate(date1), ObjConvert.toDate(date2), 0);
    }

    public static int compareTime(final String time1, final String time2) {
        String timeStr1 = new String().concat(time1).concat("000000").substring(0, 6);
        String timeStr2 = new String().concat(time1).concat("000000").substring(0, 6);

        return timeStr1.compareTo(timeStr2);
    }

    public static Calendar convertTimeZone(Calendar cal, String zone) {
        Calendar ret = Calendar.getInstance();
        TimeZone toZone = TimeZone.getTimeZone(zone);
        long curmillis = cal.getTimeInMillis();
        ret.setTimeInMillis(curmillis + toZone.getRawOffset() - cal.getTimeZone().getRawOffset());

        return ret;
    }

    /**
     * 在某个日期加指定的年或月或天
     *
     * @param precision -只能Calendar.YEAR/Calendar.MONTH/Calendar.DAY_OF_YEAR/Calendar.DAY_OF_MONTH否则返回null
     * @param amount
     * @param d1
     * @return
     */
    public static Date dateAdd(int precision, int amount, Date d1) {
        if (d1 == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d1.getTime());
        cal.add(precision, amount);
        return cal.getTime();
    }

    public static String dateAdd(int precision, int amount, String d1, String format) {
        return formatDate(dateAdd(precision, amount, ObjConvert.toDate(d1, format)), format);
    }

    public static String dateAdd(int precision, int amount, String d1) {
        return formatDate(dateAdd(precision, amount, ObjConvert.toDate(d1)));
    }

    /**
     * 日期相加
     *
     * @param date
     * @param day
     * @shew
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 返回日期毫秒
     *
     * @param date
     * @param
     * @shew
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 格式化日期
     *
     * @param dateStr  字符型日期
     * @param format 格式
     * @return 返回日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            java.text.DateFormat df = new SimpleDateFormat(format);
            date = df.parse(dateStr);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    public static void main(String[] args) {
        // System.out.println(dateAdd(Calendar.DAY_OF_MONTH, 10, "20160826"));
        // System.out.println(DateUtil.getDateTimeWithMs());
        System.out.println(DateUtil.getDateTime());

    }

//	public static String getCalendarEndDate(String sDate, String sFrq) {

    // if (CommUtil.isNull(sDate)) {
    //// sDate = ;
    // }
//
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(covStringToDate(sDate));
//		if (DateFrq.D == BasicEnumUtil.parse2Enum(DateFrq.class, sFrq)) {
//			// ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH)); //
//			// 月初
//		} else if (DateFrq.M == BasicEnumUtil.parse2Enum(DateFrq.class, sFrq)) {
//			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); // 月末
//		} else if (DateFrq.Q == BasicEnumUtil.parse2Enum(DateFrq.class, sFrq)) {
//			ca.set(Calendar.MONTH, (ca.get(Calendar.MONTH) / 3) * 3 + 2);
//			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); // 季末
//		} else if (DateFrq.Y == BasicEnumUtil.parse2Enum(DateFrq.class, sFrq)) {
//			ca.set(Calendar.MONTH, ca.getActualMaximum(Calendar.MONTH)); // 12月份
//			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); // 年末
//		} else {
//			throw new IllegalArgumentException("日期格式不合法:" + sDate + "," + sFrq);
//		}

//		return covDateToString(ca.getTime());
//	}

    public static int calDays(String startDate, String endDate, int iType, int iFlag) {
        int days = 0;

        if (!chkIsDate(startDate) || !chkIsDate(endDate)) {
            throw new IllegalArgumentException("日期格式不合法,startDate=" + startDate + ",endDate=" + endDate + ")");
        }

        if (0 == iType) {
            days = (int) ((covStringToDate(endDate).getTime() - covStringToDate(startDate).getTime())
                    / (24 * 60 * 60 * 1000));
            if (1 == iFlag) {
                days += 1;
            }
        } else {
            int years = Integer.parseInt(endDate.substring(0, 4)) - Integer.parseInt(startDate.substring(0, 4));
            int months = Integer.parseInt(endDate.substring(4, 6)) - Integer.parseInt(startDate.substring(4, 6));
            int days_tmp = Integer.parseInt(endDate.substring(6, 8)) - Integer.parseInt(startDate.substring(6, 8));

            int runflag = 0;
            int endyears = Integer.parseInt(endDate.substring(0, 4));
            int endmonths = Integer.parseInt(startDate.substring(4, 6));

            if (endyears % 4 == 0 && endyears % 100 != 0 || endyears % 400 == 0) {
                runflag = 1;
            } else {
                runflag = 0;
            }

            if (months < 0) {
                years -= 1;
                months += 12;
            }
            if (days_tmp < 0) {
                months -= 1;
                if (endmonths == 2) {
                    if (runflag == 1) {
                        days_tmp += 29;
                    } else {
                        days_tmp += 28;
                    }
                } else if (endmonths == 4 || endmonths == 6 || endmonths == 9 || endmonths == 11) {
                    days_tmp += 30;
                } else {
                    days_tmp += 31;
                }
            }
            days = 360 * years + 30 * months + days_tmp;

            if (1 == iFlag) {
                days += 1;
            }
        }

        return days;
    }

}
