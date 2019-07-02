package com.libs.view.recyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.libs.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/4/19:15:26
 * @ 功能：KRecycleView工具类
 */
public class KRecycleViewUtils {
    /**
     * 获取整体滑动的距离
     */
    public static int getScollYDistance(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
            View firstVisiableChildView = ((LinearLayoutManager) manager).findViewByPosition(position);
            int itemHeight = firstVisiableChildView.getHeight();
            return (position) * itemHeight - firstVisiableChildView.getTop();
        } else {
            LogUtil.i("只能计算LinearLayoutManager下的滑动距离");
            return 0;
        }

    }

}
