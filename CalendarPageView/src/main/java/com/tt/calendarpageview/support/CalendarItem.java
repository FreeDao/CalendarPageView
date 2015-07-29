package com.tt.calendarpageview.support;

import java.util.Calendar;

/**
 * 封装日历适配器的数据 <功能简述> </Br> <功能详细描述> </Br>
 *
 * @author kysonX
 */
public class CalendarItem {
    // 本月
    public static final int MONTH_CURRENT = 0;
    // 上月
    public static final int MONTH_PRE = 1;
    // 下月
    public static final int MONTH_NEXT = 2;

    public Calendar calendar;
    // 是否为今天
    public boolean isToday = false;
    // 是否为当月（前后会有空）
    public int monthPos;
}
