package com.libs.utils.appUtils.barUtils;

import android.app.Activity;
import android.view.Window;


/**
 * @ author：mo
 * @ data：2018/12/13
 * @ 功能：通知栏工具类
 */
public class NotificationBarUtil {

    /**
     * 获取标题栏高度
     *
     * @param ctx
     * @return
     */
    public static int getTitleHeight(Activity ctx) {
        int contentTop = ctx.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - StatusBarUtils.getHeight();
    }
    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param isVisible {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    //    public static void showNotificationBar(boolean isVisible) {
    ////        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
    //        String methodName;
    //        if (isVisible) {
    //            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
    //        } else {
    //            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
    //        }
    //        invokePanels(methodName);
    //    }


    /**
     * 反射唤醒通知栏
     *
     * @param methodName 方法名
     */
    //    private static void invokePanels(String methodName) {
    //        try {
    //            @SuppressLint("WrongConstant")
    //            Object service = k.app().getSystemService("statusbar");
    //            @SuppressLint("PrivateApi")
    //            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
    //            Method expand = statusBarManager.getMethod(methodName);
    //            expand.invoke(service);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
}
