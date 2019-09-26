package com.libs.utils.appUtils.barUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.libs.R;
import com.libs.utils.ResUtil;
import com.libs.utils.systemUtils.OSUtils;
import com.libs.utils.viewUtil.ViewUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ author：mo
 * @ data：2019/9/20:17:12
 * @ 功能：状态栏工具类
 * https://www.cnblogs.com/changyiqiang/p/6122586.html
 */
public class StatusBarUtils {
    /** 状态栏view的ID，也可使用  private static final int FAKE_STATUS_BAR_VIEW_ID = 00000000000001 */
    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.fake_status_bar_view;

    /**
     * 获取状态栏view
     *
     * @param activity
     * @return
     */
    public static View getFakeStatusBarView(Activity activity) {
        return ((ViewGroup) activity.getWindow().getDecorView()).findViewById(FAKE_STATUS_BAR_VIEW_ID);

    }

    /**
     * 获得状态栏的高度
     *
     * @return 状态栏高度
     */
    public static int getHeight() {
        //一、
        int resourceId = ResUtil.getResource().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? ResUtil.getResource().getDimensionPixelSize(resourceId) : 0;
        //二、
        //        int  statusHeight = -1;
        //        try {
        //            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
        //            Object object = aClass.newInstance();
        //            int height = Integer.parseInt(aClass.getField("status_bar_height").get(object).toString());
        //            statusHeight = ResUtil.getResource().getDimensionPixelSize(height);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        return statusHeight;
        //三、依赖于WMS(窗口管理服务的回调)【不建议使用】
        //        Rect s = new Rect();
        //        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(s);
        //        return s.top;
    }

    /**
     * 状态栏是否显示
     *
     * @param activity activity
     * @return 状态栏显示状态
     */
    public static boolean isStatusBarExists(Activity activity) {
        //方式一
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //方式二
        //        int flags = activity.getWindow().getAttributes().flags;
        //        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * 设置状态栏显示/隐藏
     *
     * @param mActivity
     * @param isShow    是否可见
     */
    public static void setStatusBar(Activity mActivity, boolean isShow) {
        //隐藏状态栏
        if (!isShow) {

            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            //            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //显示状态栏
        } else {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            //方式二
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            //方式三 style.xml中配置
            //<style name="fullScreen" parent="Theme.AppCompat.DayNight.NoActionBar">
            //        <item name="android:windowFullscreen">true</item>
            //</style>
        }
        //设置伪造状态栏显示状态，容错需要
        View fakeStatusBarView = getFakeStatusBarView(mActivity);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * 设置状态栏颜色
     *
     * @param context 上下文，尽量使用Activity
     * @param color   状态栏颜色
     */
    public static void setBackgroundColor(Context context, @ColorInt int color) {
        if (context instanceof Activity) {
            setBackgroundColor(((Activity) context).getWindow(), color);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param window 窗口，可用于Activity和Dialog等
     * @param color  状态栏颜色
     */
    public static void setBackgroundColor(@NonNull Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
            setTextDark(window, !isDarkColor(color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setBackgroundColor(window, ColorUtils.blendARGB(Color.TRANSPARENT, color, 0.5f), false);
        }
    }

    /**
     * Android 5.0 以下版本设置状态栏颜色
     *
     * @param window        窗口
     * @param color         状态栏颜色值
     * @param isTransparent 是否透明
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setBackgroundColor(@NonNull Window window, @ColorInt int color, boolean isTransparent) {
        Context context = window.getContext();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View contentView = decorView.findViewById(android.R.id.content);
        if (contentView != null) {
            contentView.setPadding(0, isTransparent ? 0 : getHeight(), 0, 0);
        }
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setBackgroundColor(color);
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
        } else {
            //            // 绘制一个和状态栏一样高的矩形 添加
            decorView.addView(ViewUtil.getView(context, color, FAKE_STATUS_BAR_VIEW_ID, LinearLayout.LayoutParams.MATCH_PARENT, getHeight()));

        }
    }

    /**
     * 设置状态栏背景图片
     *
     * @param context  上下文，尽量使用Activity
     * @param drawable 状态栏背景图片
     */
    public static void setBackground(Context context, @NonNull Drawable drawable) {
        if (context instanceof Activity) {
            setBackground(((Activity) context).getWindow(), drawable);
        }
    }

    /**
     * 设置状态栏背景图片
     *
     * @param window   窗口，可用于Activity和Dialog等
     * @param drawable 状态栏背景图片
     */
    public static void setBackground(@NonNull Window window, @NonNull Drawable drawable) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setBackground(drawable);
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
        } else {
            //            // 绘制一个和状态栏一样高的矩形 添加
            decorView.addView(ViewUtil.getView(window.getContext(), drawable, FAKE_STATUS_BAR_VIEW_ID, LinearLayout.LayoutParams.MATCH_PARENT, getHeight()));

        }
    }

    /**
     * 设置状态栏透明
     *
     * @param context 上下文，尽量使用Activity
     */
    public static void setTransparent(Context context) {
        if (context instanceof Activity) {
            setTransparent(((Activity) context).getWindow());
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param window 窗口，可用于Activity和Dialog等
     */
    public static void setTransparent(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setBackgroundColor(window, 0x80000000, true);
        }
    }
    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     */
    public static void setBackgroundColorForSwipeBack(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getHeight();
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(color);
                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0);
                        coordinatorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                coordinatorLayout.requestLayout();
                            }
                        });
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(color);
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(color);
            }
            //设置Window透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param context 上下文，尽量使用Activity
     * @param isDark  是否为黑色文字
     */
    public static void setTextDark(Context context, boolean isDark) {
        if (context instanceof Activity) {
            setTextDark(((Activity) context).getWindow(), isDark);
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param window 窗口，可用于Activity和全屏Dialog
     * @param isDark 是否为黑色文字
     */
    private static void setTextDark(Window window, boolean isDark) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (isDark) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (OSUtils.getRomType()) {
                case MIUI:
                    setMIUIDark(window, isDark);
                    break;
                case Flyme:
                    setFlymeDark(window, isDark);
                    break;
                default:
            }
        }
    }

    /**
     * 设置MIUI系统状态栏是否为黑色文字
     *
     * @param window 窗口，仅可用于Activity
     * @param isDark 是否为黑色文字
     */
    private static void setMIUIDark(Window window, boolean isDark) {
        try {
            Class<? extends Window> clazz = window.getClass();
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, isDark ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Flyme系统状态栏是否为黑色文字
     *
     * @param window 窗口
     * @param isDark 是否为黑色文字
     */
    private static void setFlymeDark(Window window, boolean isDark) {
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断颜色是否为深色
     *
     * @param color 要判断的颜色
     * @return 是否为深色
     */
    public static boolean isDarkColor(@ColorInt int color) {
        //        double darkness =
        //                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        //        return darkness >= 0.5;
        return ColorUtils.calculateLuminance(color) < 0.5;
    }
}