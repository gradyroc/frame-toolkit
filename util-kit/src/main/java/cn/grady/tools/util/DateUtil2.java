package cn.grady.tools.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 基于 java8， 弥补DateUtil的不足
 * @author
 */
public class DateUtil2 {

    /**
     * 日期加减
     * @param date 格式yyyyMMdd
     * @param days 天数
     * @return 加减后的天数
     */
    public static String dateAdd(String date, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld = LocalDate.parse(date, formatter);
        ld = ld.plusDays(days);
        return ld.format(formatter);
    }

    /**
     * 日期加减
     * @param date 格式yyyyMMdd
     * @param weeks 周数
     * @return 加减后的天数
     */
    public static String weekAdd(String date, int weeks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld = LocalDate.parse(date, formatter);
        ld = ld.plusWeeks(weeks);
        return ld.format(formatter);
    }

    /**
     * 日期加减
     * @param date 格式yyyyMMdd
     * @param months 月数
     * @return 加减后的天数
     */
    public static String monthAdd(String date, int months) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld = LocalDate.parse(date, formatter);
        ld = ld.plusMonths(months);
        return ld.format(formatter);
    }    
    
    /**
     * 日期加减
     * @param date 格式yyyyMMdd
     * @param years 年数
     * @return 加减后的天数
     */
    public static String yearAdd(String date, int years) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld = LocalDate.parse(date, formatter);
        ld = ld.plusYears(years);
        return ld.format(formatter);
    }    

    /**
     * 时间加减 HH:mm:ss
     * 
     * @param time 格式yyyyMMdd
     * @param mins 分钟数
     * @return 加减后的时间
     */
    public static String timeAdd(String time, int mins) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT);
        formatter.withResolverStyle(ResolverStyle.LENIENT);
        LocalTime ld = LocalTime.parse(time, formatter);
        ld = ld.plusMinutes(mins);
        return ld.format(formatter);
    }

    /**
     * 
     * @param date1
     * @param date2
     * @return 这个比DateUtil.compareDate的性能高一些
     */
    public static int compareDate(String date1, String date2) {
        long dateDiff = dateDiff(date1, date2);

        if (dateDiff == 0) {
            return 0;
        } else if (dateDiff < 0) {
            // date1 > date 2
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 间隔天数
     * @param date1 格式yyyyMMdd
     * @param date2 格式yyyyMMdd
     * @return 间隔天数
     */
    public static long dateDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.DAYS.between(ld1, ld2);
    }

    public static long weekDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.WEEKS.between(ld1, ld2);
    }

    public static long monthDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.MONTHS.between(ld1, ld2);
    }

    public static long quarterDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.MONTHS.between(ld1, ld2) / 3;
    }

    public static long yearDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.YEARS.between(ld1, ld2);
    }

    
    /**
     * 间隔秒数
     * @param date1 yyyyMMdd HH:mm:ss
     * @param date2 yyyyMMdd HH:mm:ss
     * @return
     */
    public static long secondDiff(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.TIME_FORMAT_LONG);
        LocalDate ld1 = LocalDate.parse(date1, formatter);
        LocalDate ld2 = LocalDate.parse(date2, formatter);
        return ChronoUnit.SECONDS.between(ld1, ld2);
    }

    
    /**
     * 周日
     * 
     * @param date 格式:yyyyMMdd
     * @return 下一个周日或date（如果date是周日）
     */
    public static String nextOrSameSunday(String date) {
        return nextOrSame(date, DayOfWeek.SUNDAY);
    }

    /**
     * 周一
     */
    public static String nextOrSameMonday(String date) {
        return nextOrSame(date, DayOfWeek.MONDAY);
    }

    /**
     * 周二
     */
    public static String nextOrSameTuesday(String date) {
        return nextOrSame(date, DayOfWeek.TUESDAY);
    }

    /**
     * 周三
     */
    public static String nextOrSameWednesday(String date) {
        return nextOrSame(date, DayOfWeek.WEDNESDAY);
    }

    /**
     * 周四
     */
    public static String nextOrSameThursday(String date) {
        return nextOrSame(date, DayOfWeek.THURSDAY);
    }

    /**
     * 周五
     */
    public static String nextOrSameFriday(String date) {
        return nextOrSame(date, DayOfWeek.FRIDAY);
    }

    /**
     * 周六
     */
    public static String nextOrSameSaturday(String date) {
        return nextOrSame(date, DayOfWeek.SATURDAY);
    }

    private static String nextOrSame(String date, DayOfWeek dayOfWeek) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATE_ONLY_FORMAT);
        LocalDate ld = LocalDate.parse(date, formatter);
        ld = ld.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        return ld.format(formatter);
    }

}
