package com.libs.utils.systemUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.libs.k;
import com.libs.modle.unit.Limits;
import com.libs.utils.logUtils.LogUtil;


/**
 * @ description: 屏幕相关工具类
 * @ author: mo
 * @ date: 2018/1/8 0008 17:26
 */

public class ScreenUtil {

    private ScreenUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("ScreenUtil cannot be instantiated");
    }

    /**
     * 窗口管理
     */
    public static WindowManager getWindowManager() {
        return (WindowManager) k.app().getSystemService(Context.WINDOW_SERVICE);
    }

    //////////////////////////////////////信息相关//////////////////////////////////////////////////////////////

    /**
     * 获取屏幕信息
     * DisplayMetrics   是Android提供的记述屏幕的有关信息的一种结构，诸如其尺寸，密度和字体缩放的一般信息。
     * <p></p>
     * int width = metrics.widthPixels;  // 表示屏幕的像素宽度，单位是px（像素）
     * int height = metrics.heightPixels;  // 表示屏幕的像素高度，单位是px（像素）
     * float density = metrics.density;  // 显示器的逻辑密度，Density Independent Pixel（如3.0） （ metrics.scaledDensity和metrics.density数值是一样的）
     * int densityDpi = metrics.densityDpi;  // 整个屏幕的像素密度DPI（dots per inch每英寸像素数），可以是密度低,密度中等,或者密度高（如240/ 360 / 480）
     * float xdpi= metrics.xdpi /表示在X轴方向上每英寸的像素值，X轴方向上的DPI（dots per inch)
     * float ydpi= metrics.ydpi //表示在Y轴方向上每英寸的像素值,  Y方向上的DPI
     */
    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm;
        //第一种获取方法
        dm = k.app().getResources().getDisplayMetrics();
        //第二种方法
//        DisplayMetrics dm = new DisplayMetrics();
//       getWindowManager().getDefaultDisplay().getMetrics(dm);

        //第三种获取方法  弃用了
//        dm = new DisplayMetrics();
//        ((WindowManager) k.app().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        return dm;
    }

    /**
     * 获得屏幕宽度
     *
     * @ return 屏幕宽
     */
    public static int getScreenWidth() {
//        方法1
//        WindowManager windowManager = getWindowManager();
//        if (windowManager == null) {
//            return ResUtil.getResource().getDisplayMetrics().widthPixels;
//        }
//        int width = 0;
//        if (Build.VERSION.SDK_INT >= 16) {
//            Point point = new Point();
//            windowManager.getDefaultDisplay().getSize(point);
//            width = point.x;
//        } else {
//            width = windowManager.getDefaultDisplay().getWidth();
//        }
//        return width;
//方法2
//        WindowManager wm = getWindowManager();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.widthPixels;

//        方法3
//        WindowManager wm = getWindowManager();
//        if (wm == null) {
//            return k.app().getResources().getDisplayMetrics().widthPixels;
//        }
//        Point point = new Point();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            wm.getDefaultDisplay().getRealSize(point);
//        } else {
//            wm.getDefaultDisplay().getSize(point);
//        }
//        return point.x;
        //        方法4
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @ return 屏幕高
     */
    public static int getScreenHeight() {
        //        方法1
//        WindowManager windowManager = getWindowManager();
//        if (windowManager == null) {
//            return ResUtil.getResource().getDisplayMetrics().heightPixels;
//        }
//        int hight = 0;
//        if (Build.VERSION.SDK_INT >= 16) {
//            Point point = new Point();
//            windowManager.getDefaultDisplay().getSize(point);
//            hight = point.y;
//        } else {
//            hight = windowManager.getDefaultDisplay().getHeight();
//        }
//        return hight;
        //方法2
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        KWindowManager.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.heightPixels;

        //        方法3
//        WindowManager wm = KWindowManager.getWindowManager();
//        if (wm == null) {
//            return k.app().getResources().getDisplayMetrics().heightPixels;
//        }
//        Point point = new Point();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            wm.getDefaultDisplay().getRealSize(point);
//        } else {
//            wm.getDefaultDisplay().getSize(point);
//        }
//        return point.y;
//        方法4
        int screenHeight = getDisplayMetrics().heightPixels;
        if(DeviceUtil.isXiaomi() && xiaomiNavigationGestureEnabled()){
            screenHeight += getResourceNavHeight();
        }
        return getDisplayMetrics().heightPixels;
    }
    private static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean xiaomiNavigationGestureEnabled() {
        int val = Settings.Global.getInt(k.app().getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
        return val != 0;
    }
    private static int getResourceNavHeight(){
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        int resourceId = k.app().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return k.app().getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    /**
     * 判断是否横屏
     */
    public static boolean isLandscape() {
        return k.app().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     */
    public static boolean isPortrait() {
        return k.app().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    //////////////////////////////////////操作相关//////////////////////////////////////////////////////////////


    /**
     * 设置屏幕方向
     *
     * @param activity             Activity
     * @param requestedOrientation 方向
     */
    public static void setScreenOrientation(Activity activity, @Limits.ScreenOrientation int requestedOrientation) {
        activity.setRequestedOrientation(requestedOrientation);
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     */
    public static void setScreenOrientationLandscape(Activity activity) {
        setScreenOrientation(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setScreenOrientationPortrait(Activity activity) {
        setScreenOrientation(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置状态栏、导航栏透明,营造沉浸式效果
     *
     * @param mActivity
     */
    public static void setBarTransparent(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置窗口全屏显示（在setContentView（）之前调用）
     *
     * @param activity activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        try {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("下面这个是原来使用的方法，要给布局设置setViewfits，先这么用，看效果");
            Window window = activity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                    View decorView = window.getDecorView();
                    //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    decorView.setSystemUiVisibility(option);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //导航栏颜色也可以正常设置
//                    if (isNavigation) {
                    window.setNavigationBarColor(Color.TRANSPARENT);
//                    }
                } else {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                    int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                    attributes.flags |= flagTranslucentStatus;
//                    if (isNavigation) {
                    attributes.flags |= flagTranslucentNavigation;
//                    }
                    window.setAttributes(attributes);
                }
            }
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }


    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }


    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return -1;
        }
    }


    /**
     * 判断是否锁屏
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) k.app()
                .getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    k.app().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                k.app().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (k.app().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 设置安全窗口，禁用系统截屏。防止 App 中的一些界面被截屏，并显示在其他设备中造成信息泄漏。
     * （常见手机设备系统截屏操作方式为：同时按下电源键和音量键。）
     *
     * @param activity
     */
    public static void noScreenshots(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 设置系统屏幕亮度模式，需要WRITE_SETTINGS权限
     *
     * @param auto 自动
     * @return 是否设置成功
     */
    public static boolean setScreenBrightnessMode(boolean auto) {
        boolean result = true;
        if (isScreenBrightnessModeAuto() != auto) {
            result = Settings.System.putInt(k.app().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    auto ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                            : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
        return result;
    }

    /**
     * 判断系统屏幕亮度模式是否是自动，需要WRITE_SETTINGS权限
     *
     * @return true：自动；false：手动；默认：true
     */
    public static boolean isScreenBrightnessModeAuto() {
        return getScreenBrightnessModeState() == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true : false;
    }

    /**
     * 获取系统屏幕亮度模式的状态，需要WRITE_SETTINGS权限
     *
     * @return System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC：自动；System.
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC
     * ：手动；默认：System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
     */
    public static int getScreenBrightnessModeState() {
        return Settings.System.getInt(k.app().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 获取系统亮度，需要WRITE_SETTINGS权限
     *
     * @param context 上下文
     * @return 亮度，范围是0-255；默认255
     */
    public static int getScreenBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    /**
     * 设置系统亮度并实时可以看到效果，需要WRITE_SETTINGS权限
     *
     * @param activity         要通过此Activity来设置窗口的亮度
     * @param screenBrightness 亮度，范围是0-255
     * @return 设置是否成功
     */
    public static boolean setScreenBrightnessAndApply(Activity activity,
                                                      int screenBrightness) {
        boolean result = true;
        result = setScreenBrightness(activity, screenBrightness);
        if (result) {
            setWindowBrightness(activity, screenBrightness);
        }
        return result;
    }

    /**
     * 设置系统亮度（此方法只是更改了系统的亮度属性，并不能看到效果。要想看到效果可以使用setWindowBrightness()方法设置窗口的亮度），
     * 需要WRITE_SETTINGS权限
     *
     * @param context          上下文
     * @param screenBrightness 亮度，范围是0-255
     * @return 设置是否成功
     */
    public static boolean setScreenBrightness(Context context,
                                              int screenBrightness) {
        int brightness = screenBrightness;
        if (screenBrightness < 1) {
            brightness = 1;
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255;
            if (brightness == 0) {
                brightness = 255;
            }
        }
        boolean result = Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, brightness);
        return result;
    }

    /**
     * 设置给定Activity的窗口的亮度（可以看到效果，但系统的亮度属性不会改变）
     *
     * @param activity         要通过此Activity来设置窗口的亮度
     * @param screenBrightness 亮度，范围是0-255
     */
    public static void setWindowBrightness(Activity activity,
                                           @FloatRange(from = 0, to = 255) float screenBrightness) {
        float brightness = screenBrightness;
        if (screenBrightness < 1) {
            brightness = 1;
        } else if (screenBrightness > 255) {
            brightness = screenBrightness % 255;
            if (brightness == 0) {
                brightness = 255;
            }
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.screenBrightness = (float) brightness / 255;
        window.setAttributes(localLayoutParams);
    }

    /**
     * 获取屏幕休眠时间，需要WRITE_SETTINGS权限
     *
     * @param context 上下文
     * @return 屏幕休眠时间，单位毫秒，默认30000
     */
    public static int getScreenDormantTime(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, 30000);
    }

    /**
     * 设置屏幕休眠时间，需要WRITE_SETTINGS权限
     *
     * @param context 上下文
     * @return 设置是否成功
     */
    public static boolean setScreenDormantTime(Context context, int millis) {
        return Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, millis);
    }
}
