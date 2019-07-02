package com.libs.modle.listener.touchListener;

/**
 * @ author：mo
 * @ data：2017/11/29 0029
 * @ 功能：item触摸移动监听
 */
public interface KOnItemTouchMoveListener {
    /**
     * 当Item上下拖动时调用
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 当item左右滑动时调用
     */
    boolean onItemRemove(int position);
}
