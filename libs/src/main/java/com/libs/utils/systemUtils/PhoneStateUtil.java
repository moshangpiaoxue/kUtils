package com.libs.utils.systemUtils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.libs.k;


/**
 * @ author：mo
 * @ data：2019/2/13:15:21
 * @ 功能：手机状态相关
 */
public class PhoneStateUtil {
    /**
     * 判断屏幕是否处于锁屏状态 (黑屏或锁屏)
     *
     * @return 是否处于锁屏状态
     */
    public static boolean isScreenLocked() {
        KeyguardManager km = (KeyguardManager) k.app().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 判断屏幕是否亮着
     *
     * @return 是否亮着
     */
    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) k.app()    .getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
}
