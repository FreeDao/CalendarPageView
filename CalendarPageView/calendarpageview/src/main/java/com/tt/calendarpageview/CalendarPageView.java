package com.tt.calendarpageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.calendarpageview.support.OnDaySelectedChangedListener;

import java.util.Calendar;

/**
 * use this view , month view with weekday title
 * Author: Kyson on 2015/7/28 15:00
 * Blog: www.hikyson.cn
 */
public class CalendarPageView extends LinearLayout {

    private Context mContext;

    private CalendarCardView mCalendarCardView;

    public CalendarPageView(Context context) {
        this(context, null);
    }

    public CalendarPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int weekTextStyle;
        TypedArray typedArray = null;
        // 获取所有的资源文件
        if (attrs != null) {
            typedArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.TtCalendar);
            //星期是card
            weekTextStyle = typedArray.getResourceId(
                    R.styleable.TtCalendar_weekTextStyle,
                    R.style.textView_sp12_green);
        } else {
            weekTextStyle = R.style.textView_sp13_grey_bg_bold;
        }
        inflate(mContext, R.layout.widget_calendar_pageview, this);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout weekdaysLl = (LinearLayout) this
                .findViewById(R.id.widget_calendar_card_weekdays);
        // 设置星期文字样式
        for (int i = 0; i < weekdaysLl.getChildCount(); i++) {
            View child = weekdaysLl.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextAppearance(mContext, weekTextStyle);
            }
        }
        mCalendarCardView = (CalendarCardView) this.findViewById(R.id.widget_calendar_pageview_card);
        mCalendarCardView.setTypedArray(typedArray);
    }

    public void setCurrentMonth(Calendar currentMonth) {
        mCalendarCardView.setCurrentMonth(currentMonth);
    }

    public void selectCal(Calendar selectedCal) {
        mCalendarCardView.selectCal(selectedCal);
    }

    public void setOnDaySelectListener(
            OnDaySelectedChangedListener onDaySelectListener) {
        mCalendarCardView.setOnDaySelectListener(onDaySelectListener);
    }

}
