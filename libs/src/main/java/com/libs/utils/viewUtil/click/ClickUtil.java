package com.libs.utils.viewUtil.click;

/**
 * @ author：mo
 * @ data：2018/12/28
 * @ 功能：点击工具类
 */
public class ClickUtil {
    /**
     * 间隔时间
     */
    public static  int MIN_CLICK_DELAY_TIME = 1000;
    /**
     * 上次点击时间
     */
    private static long lastClickTime;
    /**
     * 默认时间内（1秒），是否是重复点击
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /**
     * 规定时间内，是否是重复点击
     */
    public static boolean isFastClick(long millis) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= millis) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
