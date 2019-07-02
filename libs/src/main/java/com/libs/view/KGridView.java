package com.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 自定义gridview解决listview嵌套gridview时，gridview里只显示一行的bug，
 */

public class KGridView extends GridView {
    public KGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KGridView(Context context) {
        super(context);
    }

    public KGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //核心在此
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec+50);
    }
}
