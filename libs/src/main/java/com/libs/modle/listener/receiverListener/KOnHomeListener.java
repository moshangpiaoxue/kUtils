package com.libs.modle.listener.receiverListener;

/**
 * @ author：mo
 * @ data：2018/12/14
 * @ 功能：home键监听
 */
public interface KOnHomeListener {
    /**
     * @param status 1=短按Home键
     *               2=长按Home键 或者 activity切换键
     *               3=锁屏
     *               4=samsung 长按Home键
     */
    void onHomeTouch(int status);
}
