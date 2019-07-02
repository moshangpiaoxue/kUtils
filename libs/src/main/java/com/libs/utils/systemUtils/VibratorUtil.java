package com.libs.utils.systemUtils;

import android.os.Vibrator;


import com.libs.k;
import com.libs.utils.appUtils.PermissionUtil;
import com.libs.utils.logUtils.LogUtil;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * @ author：mo
 * @ data：2019/1/4
 * @ 功能：震动工具类
 * 权限： <uses-permission android:name="android.permission.VIBRATE"/>
 */
public class VibratorUtil {
    /**
     * 获取震动管理器
     */
    public static Vibrator getVibrate() {
        return (Vibrator) k.app().getSystemService(VIBRATOR_SERVICE);
    }

    /**
     * 判断是否有震动器
     */
    public static Boolean isHasVibrate() {
        return getVibrate().hasVibrator();
    }

    /**
     * 开启单次震动
     *
     * @param vibrateTime 持续时长
     */
    public static Vibrator getOnce(long vibrateTime) {
        Vibrator vibrator = getVibrate();
        if (PermissionUtil.INSTANCE.checkSelfPermission(android.Manifest.permission.VIBRATE)) {
            vibrator.vibrate(vibrateTime);
        } else {
            LogUtil.i("加震动权限");
        }
        return vibrator;
    }

    /**
     * 多次震动
     *
     * @param patter 数组：long[0,1,2,3,4] long[0]=静止时间，long[1]=震动时间，long[3]=静止时间，long[4]=震动时间，可以一直添加下去
     * @param loop   循环；从patter数组的第loop位开始，==-1为不循环
     * @return
     */
    public static Vibrator getMany(long[] patter, int loop) {
        Vibrator vibrator = getVibrate();
        vibrator.vibrate(patter, loop);
        return vibrator;
    }

    /**
     * 取消震动
     *
     * @param vibrator
     */
    public static void cancle(Vibrator vibrator) {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
