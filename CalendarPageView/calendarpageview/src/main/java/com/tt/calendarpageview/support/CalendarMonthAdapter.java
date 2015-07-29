package com.tt.calendarpageview.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tt.calendarpageview.R;
import com.tt.calendarpageview.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 日历的适配器 <功能简述> <Br>
 * <功能详细描述> <Br>
 *
 * @author kysonX
 */
public class CalendarMonthAdapter extends BaseAdapter {

    private OnDaySelectedChangedListener mOnDaySelectListener;

    private List<CalendarItem> mCalendarItems = new ArrayList<CalendarItem>();
    // 当前选中的日期
    private Calendar mSelectedCal;

    private LayoutInflater mInflater;

    private Context mContext;

    //default styles of cal

    private int mTodayTextStyle = R.style.textView_sp12_green;

    private int mNotCurrentTextStyle = R.style.textView_sp12_grey_light;

    private int mDayTextStyle = R.style.textView_sp12_white;

    private int mDaySelector = R.drawable.widget_item_calendar_cardview_selector;

    public CalendarMonthAdapter(Context context, TypedArray typedArray, Calendar selectedCal, List<CalendarItem> calendarItems) {
        this.mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSelectedCal = selectedCal;
        this.mCalendarItems = calendarItems;
        initStyle(typedArray);
    }

    public void initStyle(TypedArray typedArray) {
        if (typedArray != null) {
            this.mTodayTextStyle = typedArray.getResourceId(
                    R.styleable.TtCalendar_todayTextStyle,
                    R.style.textView_sp12_green);
            this.mNotCurrentTextStyle = typedArray.getResourceId(
                    R.styleable.TtCalendar_notCurrentTextStyle,
                    R.style.textView_sp12_grey_light);
            this.mDayTextStyle = typedArray.getResourceId(
                    R.styleable.TtCalendar_dayTextStyle,
                    R.style.textView_sp12_white);
            this.mDaySelector = typedArray.getResourceId(R.styleable.TtCalendar_daySelector,
                    R.drawable.widget_item_calendar_cardview_selector);
            typedArray.recycle();
        }
    }

    @Override
    public int getCount() {
        if (mCalendarItems == null || mCalendarItems.isEmpty()) {
            return 0;
        }
        return mCalendarItems.size();
    }

    @Override
    public CalendarItem getItem(int position) {
        if (mCalendarItems == null || mCalendarItems.isEmpty()) {
            return null;
        }
        return mCalendarItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder;
        if (convertView == null) {
            holder = new GridViewHolder();
            convertView = mInflater.inflate(
                    R.layout.widget_item_calendar_cardview, parent,
                    false);
            holder.tvDay = (TextView) convertView
                    .findViewById(R.id.widget_item_calendar_cardview_date);
            convertView.setTag(holder);
        } else {
            holder = (GridViewHolder) convertView.getTag();
        }
        final CalendarItem calendarItem = getItem(position);
        TextView tvDay = holder.tvDay;
        tvDay.setText(String.valueOf(calendarItem.calendar
                .get(Calendar.DAY_OF_MONTH)));
        tvDay.setTextAppearance(mContext, getTextStyle(calendarItem));
        tvDay.setBackgroundResource(mDaySelector);
        tvDay.setSelected(CalendarUtil.isSameDay(mSelectedCal,
                calendarItem.calendar));
        tvDay.setEnabled(calendarItem.monthPos == CalendarItem.MONTH_CURRENT);
        tvDay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkItem(calendarItem.calendar);
            }
        });
        return convertView;
    }

    // check one item
    private void checkItem(Calendar cal) {
        // 点击的和当前选中的是同一天
        if (CalendarUtil.isSameDay(cal, mSelectedCal)) {
            return;
        }
        mSelectedCal = (Calendar) cal.clone();
        notifyDataSetChanged();
        if (mOnDaySelectListener != null) {
            mOnDaySelectListener.onDaySelectedChanged(mSelectedCal);
        }
    }

    /**
     * get textview's color <功能简述>
     *
     * @param calendarItem
     * @return
     */
    private int getTextStyle(CalendarItem calendarItem) {
        int style;
        if (calendarItem.monthPos == CalendarItem.MONTH_CURRENT) {
            // current month
            if (calendarItem.isToday) {
                style = mTodayTextStyle;
            } else {
                style = mDayTextStyle;
            }
        } else {
            // 非本月
            style = mNotCurrentTextStyle;
        }
        return style;
    }

    public static class GridViewHolder {
        public TextView tvDay;
    }

    public void setOnDaySelectListener(
            OnDaySelectedChangedListener onDaySelectListener) {
        this.mOnDaySelectListener = onDaySelectListener;
    }

    public List<CalendarItem> getDatas() {
        return mCalendarItems;
    }

    public void setDatas(List<CalendarItem> calendarItems) {
        this.mCalendarItems = calendarItems;
    }

    public void setSelectedDate(Calendar cal) {
        checkItem(cal);
    }

    public Calendar getSelectedDate() {
        return mSelectedCal;
    }

}
