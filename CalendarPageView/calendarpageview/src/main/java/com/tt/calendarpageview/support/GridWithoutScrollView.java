package com.tt.calendarpageview.support;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不带滚动属性的gridview <功能简述> <Br>
 * <功能详细描述> <Br>
 *
 * @author kysonX
 */
public class GridWithoutScrollView extends GridView {
    public GridWithoutScrollView(Context context) {
        super(context);
    }

    public GridWithoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
