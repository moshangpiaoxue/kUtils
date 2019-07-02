package com.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 自定义listview 解决兼容scrollview时的显示bug
 */

public class KListView extends ListView {

    public KListView(Context context) {
        super(context);
    }

    public KListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KListView(Context context, AttributeSet attrs,
                     int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    /**
     * 手动计算出listView的高度，但是不再具有滚动效果
     */
    public  void fixListViewHeight() {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = this.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, this);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度
            totalHeight += listViewItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = this.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (this.getDividerHeight() * (listAdapter.getCount() - 1));
        this.setLayoutParams(params);
    }
}