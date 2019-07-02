package com.libs.modle.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.libs.k;
import com.libs.utils.ResUtil;
import com.libs.utils.logUtils.LogUtil;



/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：窗口管理
 */
public class KWindowManager {
    /**
     * 获取窗口管理器
     */
    public static WindowManager getWindowManager() {
        return (WindowManager) k.app().getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取窗口管理器布局参数
     */
    public static WindowManager.LayoutParams getWindowManagerLayoutParams() {
        return new WindowManager.LayoutParams();
    }

    /**
     * 设置窗口全屏显示（在setContentView（）之前调用）
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            LogUtil.i("11111");
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager windowManager = KWindowManager.getWindowManager();
        if (windowManager==null){
            return ResUtil.getResource().getDisplayMetrics().widthPixels;
        }
        int width = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            width = point.x;
        } else {
            width = windowManager.getDefaultDisplay().getWidth();
        }
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager windowManager = KWindowManager.getWindowManager();
        if (windowManager==null){
            return ResUtil.getResource().getDisplayMetrics().heightPixels;
        }
        int hight = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            hight = point.y;
        } else {
            hight = windowManager.getDefaultDisplay().getHeight();
        }
        return hight;
    }
}
