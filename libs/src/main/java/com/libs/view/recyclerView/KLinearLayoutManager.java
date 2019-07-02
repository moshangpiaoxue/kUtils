package com.libs.view.recyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * author：mo
 * data：2017/11/9 0009
 * 功能：
 */

public class KLinearLayoutManager {
    /**
     * 获取水平方向的线性布局管理器
     *
     * @param context
     * @return
     */
    public static LinearLayoutManager getLinearLayoutManagerHORIZONTAL(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }
}
