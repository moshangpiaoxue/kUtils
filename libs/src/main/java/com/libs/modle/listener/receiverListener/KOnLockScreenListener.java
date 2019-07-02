package com.libs.modle.listener.receiverListener;

/**
 * @ author：mo
 * @ data：2018/12/14
 * @ 功能：
 */
public interface KOnLockScreenListener {
    /**
     * status;true=开屏
     * false=锁屏
     */
    void onScreenChange(Boolean status);
}
