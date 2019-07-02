package com.libs.utils.appUtils.barUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.libs.utils.ResUtil;
import com.libs.utils.dataUtil.dealUtil.ColorUtils;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.viewUtil.ViewUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * @ author：mo
 * @ data：2018/12/12
 * @ 功能：状态栏工具类
 * https://www.cnblogs.com/changyiqiang/p/6122586.html
 */
public class SratusBarUtil {
    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    /**
     * 通过颜色修改状态栏setStatusBarColor
     */
    private static final int FAKE_STATUS_BAR_VIEW_ID = 00000000000001;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;

    /**
     * 状态栏是否显示
     *
     * @param activity activity
     * @return
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
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
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
        isShowFakeView(mActivity, isShow);
    }

    /**
     * 设置全屏  在setContentView之前调用，并且要给布局设置setViewfits
     *
     * @param activity
     */
    public static void fullScreen(Activity activity, Boolean isNavigation) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                if (isNavigation) {
                    window.setNavigationBarColor(Color.TRANSPARENT);
                }
            } else {
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                if (isNavigation) {
                    attributes.flags |= flagTranslucentNavigation;
                }
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 设置状态栏
     *
     * @param activity       载体
     * @param color          颜色值；0==不对状态栏进行颜色上的设置，默认为白色的效果
     * @param statusBarAlpha 透明度；0==完全透明 255=完全不透明，黑色了
     */
    public static void setStatusBar(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        LogUtil.i("Build.VERSION.SDK_INT==" + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        int backgroundColor = 0;
        if (color == 0 && statusBarAlpha != 0) {
            backgroundColor = Color.argb(statusBarAlpha, 0, 0, 0);
        } else if (color != 0 && statusBarAlpha == 0) {
            backgroundColor = ResUtil.getColor("#FFFFFF");
        } else {
            backgroundColor = ColorUtils.getColor(color, statusBarAlpha);
        }

        transparentStatusBar(activity);
        ViewUtil.setViewfits(activity, true);
        setStatusBar(activity, true);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(backgroundColor);
        } else {
            decorView.addView(ViewUtil.getView(activity, backgroundColor, statusBarAlpha, FAKE_STATUS_BAR_VIEW_ID, LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        }
    }

    /**
     * 设置状态栏背景图片
     *
     * @param activity
     * @param drawable
     */
    public static void setStatusBar(Activity activity, Drawable drawable) {
        LogUtil.i("Build.VERSION.SDK_INT==" + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        transparentStatusBar(activity);
        ViewUtil.setViewfits(activity, true);
        setStatusBar(activity, true);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackground(drawable);
        } else {
            decorView.addView(ViewUtil.getView(activity, drawable, FAKE_STATUS_BAR_VIEW_ID, LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        }
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setStatusBarColorForSwipeBack(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight();
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(ColorUtils.getColor(color, statusBarAlpha));
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
                    coordinatorLayout.setStatusBarBackgroundColor(ColorUtils.getColor(color, statusBarAlpha));
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(ColorUtils.getColor(color, statusBarAlpha));
            }
            setTransparentForWindow(activity);
        }
    }

    /**
     * 设置状态栏字体，图标颜色--》黑
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBareBlack(Activity activity) {
        setMIUIStatusBarDarkIcon(activity, true);
        setMeizuStatusBarDarkIcon(activity, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 设置状态栏字体，图标颜色--》白
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBareWhite(Activity activity) {
        setMIUIStatusBarDarkIcon(activity, false);
        setMeizuStatusBarDarkIcon(activity, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 修改 MIUI V6  以上状态栏颜色
     */
    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Window透明
     */
    private static void setTransparentForWindow(Activity activity) {
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

    /**
     * 添加指定透明度矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            contentView.addView(ViewUtil.getView(activity, 0, statusBarAlpha, FAKE_STATUS_BAR_VIEW_ID, LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        }
    }


    /**
     * 设置伪造状态栏显示状态，容错需要
     *
     * @param activity
     */
    private static void isShowFakeView(Activity activity, Boolean isShow) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeTranslucentView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeTranslucentView != null) {
            fakeTranslucentView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }
}
