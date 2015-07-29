package com.tt.calendarpageview.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * util of calendar or time
 * Created by Kyson on 2015/7/28.
 */
public class CalendarUtil {

    public static long getSystemTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取时间的每个域 格式：年月日时分秒 <功能简述>
     *
     * @param time
     * @return
     */
    public static int[] getTimeFields(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int[] timeFields = new int[6];
        timeFields[0] = calendar.get(Calendar.YEAR);
        timeFields[1] = calendar.get(Calendar.MONTH);
        timeFields[2] = calendar.get(Calendar.DAY_OF_MONTH);
        timeFields[3] = calendar.get(Calendar.HOUR_OF_DAY);
        timeFields[4] = calendar.get(Calendar.MINUTE);
        timeFields[5] = calendar.get(Calendar.SECOND);
        return timeFields;
    }

    /**
     * 获取格式化的时间 <功能简述>
     *
     * @param formatter
     * @param time
     * @return
     */
    public static String getFormatTime(String formatter, long time) {
        SimpleDateFormat format = new SimpleDateFormat(formatter,
                Locale.getDefault());
        return format.format(new Date(time));
    }

    /**
     * 判断时间是否为今天 <功能简述>
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {
        long now = getSystemTime();
        int[] nowFields = getTimeFields(now);
        int[] timeFields = getTimeFields(time);
        return nowFields[0] == timeFields[0] && nowFields[1] == timeFields[1]
                && nowFields[2] == timeFields[2];
    }

    public static boolean isToday(Calendar time) {
        long now = getSystemTime();
        int[] nowFields = getTimeFields(now);
        int[] timeFields = getTimeFields(time.getTimeInMillis());
        return nowFields[0] == timeFields[0] && nowFields[1] == timeFields[1]
                && nowFields[2] == timeFields[2];
    }

    /**
     * 比较两个日期是否为同一天 <功能简述>
     *
     * @param fromCalendar
     * @param toCalendar
     * @return
     */
    public static boolean isSameDay(Calendar fromCalendar, Calendar toCalendar) {
        if (fromCalendar == null || toCalendar == null) {
            return false;
        }
        // 年月日都一样，则为同一天
        return fromCalendar.get(Calendar.YEAR) == toCalendar.get(Calendar.YEAR)
                && fromCalendar.get(Calendar.MONTH) == toCalendar
                .get(Calendar.MONTH)
                && fromCalendar.get(Calendar.DAY_OF_MONTH) == toCalendar
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否为同一个月 <功能简述>
     *
     * @param fromCalendar
     * @param toCalendar
     * @return
     */
    public static boolean isSameMonth(Calendar fromCalendar, Calendar toCalendar) {
        if (fromCalendar == null || toCalendar == null) {
            return false;
        }
        // 年月都一样，则为同一月
        return fromCalendar.get(Calendar.YEAR) == toCalendar.get(Calendar.YEAR)
                && fromCalendar.get(Calendar.MONTH) == toCalendar
                .get(Calendar.MONTH);
    }

    /**
     * 获取所有时间 <功能简述>
     *
     * @param time
     * @return
     */
    public static String getAllFormatTime(long time) {
        return getFormatTime("yyyy-MM-dd HH:mm:ss", time);
    }

    public static String getYMDHMFormatTime(long time) {
        return getFormatTime("yyyy/MM/dd HH:mm", time);
    }

    /**
     * 获取时分 <功能简述>
     *
     * @param time
     * @return
     */
    public static String getHMFormatTime(long time) {
        return getFormatTime("HH:mm", time);
    }

    public static String getYMDFormatTime(long time) {
        return getFormatTime("yyyy/MM/dd", time);
    }

    /**
     * 获取时分 秒<功能简述>
     *
     * @param time
     * @return
     */
    public static String getHMSFormatTime(long time) {
        return getFormatTime("HH时mm分ss秒", time);
    }


}
