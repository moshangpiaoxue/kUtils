package com.libs.utils.systemUtils.fingerUtil;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;

/**
 * @ author：mo
 * @ data：2019/5/17:13:50
 * @ 功能：
 */
public enum FingerDentifyUtil {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 判断版本大于9.0
     */
    private boolean isAboveApi28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断版本大于6.0
     */
    private boolean isAboveApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断是否设置了锁屏密码（设置指纹，必设密码）
     */
    public boolean isKeyguardSecure(Activity mActivity) {
        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }
        return false;
    }
}
