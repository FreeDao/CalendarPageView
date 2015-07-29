package com.tt.calendarpageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;

import com.tt.calendarpageview.support.CalendarItem;
import com.tt.calendarpageview.support.CalendarMonthAdapter;
import com.tt.calendarpageview.support.GridWithoutScrollView;
import com.tt.calendarpageview.support.OnDaySelectedChangedListener;
import com.tt.calendarpageview.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * core month view
 */
public class CalendarCardView extends GridWithoutScrollView {

    private Context mContext;

    private CalendarMonthAdapter mGridViewAdapter;

    // 当前显示的月份
    private Calendar mCurrentMonth = Calendar.getInstance();

    /**
     * 选择日期监听
     */
    private OnDaySelectedChangedListener mOnDaySelectListener;

    private TypedArray mTypedArray;


    public CalendarCardView(Context context) {
        this(context, null);
    }

    public CalendarCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 初始化日期以及view等控件
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            mTypedArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.TtCalendar);
        }
        setGravity(Gravity.CENTER_HORIZONTAL);
        setSelector(R.color.transparent);
        setNumColumns(Calendar.DAY_OF_WEEK);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setStretchMode(STRETCH_COLUMN_WIDTH);

        mGridViewAdapter = new CalendarMonthAdapter(mContext, mTypedArray, Calendar.getInstance(), getGvDataByYearAndMonth());

        // 点击item回调
        mGridViewAdapter
                .setOnDaySelectListener(new OnDaySelectedChangedListener() {

                    @Override
                    public void onDaySelectedChanged(Calendar dateSelected) {
                        if (mOnDaySelectListener != null) {
                            mOnDaySelectListener
                                    .onDaySelectedChanged(dateSelected);
                        }
                    }
                });
        this.setAdapter(mGridViewAdapter);
    }

    public TypedArray getTypedArray() {
        return mTypedArray;
    }

    public void setTypedArray(TypedArray mTypedArray) {
        this.mTypedArray = mTypedArray;
        mGridViewAdapter.initStyle(mTypedArray);
    }

    /**
     * 设置当前显示的月份 <功能简述>
     *
     * @param currentMonth
     */
    public void setCurrentMonth(Calendar currentMonth) {
        mCurrentMonth = (Calendar) currentMonth.clone();
        notifyMonthChanged();
    }

    /**
     * month changed
     */
    private void notifyMonthChanged() {
        mGridViewAdapter.setDatas(getGvDataByYearAndMonth());
        mGridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 选中日期 <功能简述>
     *
     * @param selectedCal
     */
    public void selectCal(Calendar selectedCal) {
        mGridViewAdapter.setSelectedDate(selectedCal);
        mGridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 给gridview设置,设置数据 <功能简述>
     */
    private List<CalendarItem> getGvDataByYearAndMonth() {
        // 前面的空格数
        int firstDaySpaceCount = getFirstDayOfSpaceCount(mCurrentMonth);
        // 后面的空格数
        int lastDaySpaceCount = getLastDayOfSpaceCount(mCurrentMonth);
        // 获取当前月有多少天
        int dayCount = getDayNumInMonth(mCurrentMonth);

        return getGvListData(firstDaySpaceCount, lastDaySpaceCount, dayCount);
    }

    /**
     * 为gridview中添加需要展示的数据
     */
    private List<CalendarItem> getGvListData(int first, int last, int dayCount) {
        List<CalendarItem> list = new ArrayList<CalendarItem>();
        // 当前选中的月份对应的calendar
        Calendar currentCalendar = (Calendar) mCurrentMonth.clone();
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // 前面的空格，填充
        for (int i = 0; i < first; i++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, getDayNumInMonth(calendar)
                    - first + i + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = CalendarUtil.isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_PRE;
            list.add(calendarItem);
        }
        // 当前月的日期
        for (int j = 0; j < dayCount; j++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.set(Calendar.DAY_OF_MONTH, j + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = CalendarUtil.isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_CURRENT;
            list.add(calendarItem);
        }

        // 后面的空格填充
        for (int k = 0; k < last; k++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, k + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = CalendarUtil.isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_NEXT;
            list.add(calendarItem);
        }
        return list;
    }

    /**
     * 获取月份第一天前面的空格 <功能简述>
     *
     * @param cal
     * @return
     */
    private static int getFirstDayOfSpaceCount(Calendar cal) {
        Calendar calTemp = (Calendar) cal.clone();
        calTemp.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayInWeek = calTemp.get(Calendar.DAY_OF_WEEK);
        // 换算为空格数
        return weekToSpaceCount(firstDayInWeek);
    }

    /**
     * 获取月份最后一天后面的空格数 <功能简述>
     *
     * @param cal
     * @return
     */
    private static int getLastDayOfSpaceCount(Calendar cal) {
        Calendar calTemp = (Calendar) cal.clone();
        calTemp.set(Calendar.DAY_OF_MONTH, getDayNumInMonth(cal));
        int lastDayInWeek = calTemp.get(Calendar.DAY_OF_WEEK);
        return 6 - weekToSpaceCount(lastDayInWeek);
    }

    /**
     * 将周几换算为空格数 <功能简述>
     *
     * @param week
     * @return
     */
    private static int weekToSpaceCount(int week) {
        int space = (Calendar.DAY_OF_WEEK + (week - 2)) % Calendar.DAY_OF_WEEK;
        return space;
    }

    /**
     * 获取当前月的总共天数
     *
     * @param cal
     * @return
     */
    private static int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    public OnDaySelectedChangedListener getOnDaySelectListener() {
        return mOnDaySelectListener;
    }

    public void setOnDaySelectListener(
            OnDaySelectedChangedListener mOnDaySelectListener) {
        this.mOnDaySelectListener = mOnDaySelectListener;
    }

}
