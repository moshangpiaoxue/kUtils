package com.libs.modle.listener.clickListener;

import android.view.View;

/**
 * @ author：mo
 * @ data：2017/11/29：14:14
 * @ 功能：item点击事件监听接口
 */
public interface KOnItemClickListener {
    /**
     * 普通点击回调
     *
     * @param view     点击的子项view
     * @param position 子项的的位置
     */
    void onItemClick(View view, int position);
    /**
     * 长按点击回调
     *
     * @param view     点击的子项view
     * @param position 子项的的位置
     */
    void onItemLongClick(View view, int position);
}
