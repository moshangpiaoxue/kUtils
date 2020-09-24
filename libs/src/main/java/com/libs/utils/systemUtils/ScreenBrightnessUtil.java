package com.libs.utils.systemUtils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import com.libs.k;


/**
 * @ author：mo
 * @ data：2019/2/13:15:21
 * @ 功能：手机状态相关
 */
public class ScreenBrightnessUtil {
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
        PowerManager pm = (PowerManager) k.app().getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
    /**
     * 判断是否开启了自动亮度调节
     */

    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    /**
     * 获取屏幕的亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     *
     * @param activity
     * @param brightness
     */
    public static void setBrightness(Activity activity, int brightness) {
        // Settings.System.putInt(activity.getContentResolver(),

        // Settings.System.SCREEN_BRIGHTNESS_MODE,

        // Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        Log.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness);

        activity.getWindow().setAttributes(lp);
    }

    // 那么，能设置了，但是为什么还是会出现，设置了，没反映呢？

    // 嘿嘿，那是因为，开启了自动调节功能了，那如何关闭呢？这才是最重要的：

    /**
     * 停止自动亮度调节
     *
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

                Settings.System.SCREEN_BRIGHTNESS_MODE,

                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }


    /**
     * 开启亮度自动调节
     *
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

                Settings.System.SCREEN_BRIGHTNESS_MODE,

                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    // 至此，应该说操作亮度的差不多都有了，结束！
    // 哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！

    /**
     * 保存亮度设置状态
     *
     * @param resolver
     * @param brightness
     */
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = Settings.System
                .getUriFor("screen_brightness");

        Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        // resolver.registerContentObserver(uri, true, myContentObserver);
        resolver.notifyChange(uri, null);
    }
}
