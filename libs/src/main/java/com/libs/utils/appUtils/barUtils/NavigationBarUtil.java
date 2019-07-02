package com.libs.utils.appUtils.barUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.libs.k;
import com.libs.utils.ResUtil;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


/**
 * @ author：mo
 * @ data：2018/12/13：10:39
 * @ 功能：底部导航栏工具类
 */
public class NavigationBarUtil {

    /**
     * 是否有导航栏
     */
    @SuppressLint("NewApi")
    public static boolean isNavigationBarHas() {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        return !ViewConfiguration.get(k.app()).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK) ? true : false;
//        boolean isHave = false;
//        Resources rs = ResUtil.getResource();
//        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (id > 0) {
//            isHave = rs.getBoolean(id);
//        }
//        try {
//            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
//            Method m = systemPropertiesClass.getMethod("get", String.class);
//            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
//            if ("1".equals(navBarOverride)) {
//                isHave = false;
//            } else if ("0".equals(navBarOverride)) {
//                isHave = true;
//            }
//        } catch (Exception e) {
//            LogUtil.i(e);
//        }
//        return isHave;
    }

    /**
     * 是否有导航栏
     */
    public static boolean isNavigationBarHas(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = mActivity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(mActivity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 导航栏显示状态
     * @param mActivity
     * @return
     */
    public static boolean isNavBarVisible(Activity mActivity) {
        Window window = mActivity.getWindow();
        boolean isNoLimits = (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
        if (isNoLimits) {
            return false;
        }
        return (window.getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }



    /**
     * 设置导航栏显示状态
     *
     * @param activity
     * @param isShow   true=显示 false=隐藏
     *                 View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY：有这个参数，用户只能上划原导航栏区域才能让导航栏再次出现
     *                 没这个参数，用户点击屏幕大概1秒之后就会出现，并且消费掉了这次的点击事件
     */
    public static void setNavBarVisibility(Activity activity, Boolean isShow) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(isShow ? View.VISIBLE : View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            int uiOptions2 = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            decorView.setSystemUiVisibility(isShow ? uiOptions2 : uiOptions);
        }
    }
    /**
     * 设置导航栏沉浸式
     *
     * @param activity activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(final Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    static final Set<String> NO_NAVIGATION_BAR_MODEL_SET = new HashSet<>();

    static {
        NO_NAVIGATION_BAR_MODEL_SET.add("Nexus 4");
        NO_NAVIGATION_BAR_MODEL_SET.add("H60-L01");
        NO_NAVIGATION_BAR_MODEL_SET.add("P7-L07");
        NO_NAVIGATION_BAR_MODEL_SET.add("MT7-UL00");
        NO_NAVIGATION_BAR_MODEL_SET.add("HUAWEI P7-L07");
        NO_NAVIGATION_BAR_MODEL_SET.add("OPPO R7s");
        NO_NAVIGATION_BAR_MODEL_SET.add("Xiaomi HM Note 1S");
    }


    /**
     * 获取底部导航栏高度
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        int navigationBarHeight = 0;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(activity)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }
    /**
     * 导航栏高度
     */
    public static int getNavBarHeight() {
        if (!isNavigationBarHas()) {
            return 0;
        }
        Resources res = ResUtil.getResource();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId != 0 ? res.getDimensionPixelSize(resourceId) : 0;
//        return ResUtil.getResource().getDimensionPixelSize(ResUtil.getResource().getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    /**
     * 检测是否具有底部导航栏
     *
     * @param activity
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (NO_NAVIGATION_BAR_MODEL_SET.contains(Build.MODEL)) {
                hasNavigationBar = false;
            } else {
                hasNavigationBar = newCheckDeviceHasNavigationBar(activity);
            }
        } else {
            hasNavigationBar = oldCheckDeviceHasNavigationBar(activity);
        }
        return hasNavigationBar;
    }

    private static boolean oldCheckDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar = false;
        Resources resources = activity.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    private static boolean newCheckDeviceHasNavigationBar(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        }
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}






