package com.libs.modle.listener.clickListener;

import android.view.View;

import java.util.Calendar;

/**
 * @ author：mo
 * @ data：2017/8/29：14:17
 * @ 功能：防止过快点击造成多次事件
 */
public abstract class KNoDoubleClickListener implements View.OnClickListener {
    /**
     *  两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    /**
     * 最后一次的点击时间
     */
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}