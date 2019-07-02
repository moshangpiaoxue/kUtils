package com.libs.utils.viewUtil.click;

import android.view.View;

/**
 * @ author：mo
 * @ data：2018/12/28
 * @ 功能：快速点击监听（在规定时间内不能重复点击）
 */
public abstract class OnMultiClickListener implements View.OnClickListener {
    /**
     * 两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    private static long MIN_CLICK_DELAY_TIME = 1000;

    public OnMultiClickListener() {
    }

    public OnMultiClickListener(long millis) {
        MIN_CLICK_DELAY_TIME = millis;
    }

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        if (ClickUtil.isFastClick(MIN_CLICK_DELAY_TIME)) {
            onMultiClick(v);
        }
    }
}
