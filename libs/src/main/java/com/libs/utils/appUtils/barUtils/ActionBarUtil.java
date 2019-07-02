package com.libs.utils.appUtils.barUtils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

/**
 * @ author：mo
 * @ data：2018/2/23 0023
 * @ 功能：ActionBar 工具类
 */
public class ActionBarUtil {
    /**
     * 获取ActionBar
     *
     * @param activity
     * @return
     */
    public static ActionBar getActionBar(@NonNull final AppCompatActivity activity) {
        return activity.getSupportActionBar();
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(@NonNull final Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 显示状态栏
     *
     * @param activity
     */
    public static void showActionBar(@NonNull final AppCompatActivity activity) {
        getActionBar(activity).show();
    }

    /**
     * 隐藏状态栏
     *
     * @param activity
     */
    public static void hideActionBar(@NonNull final AppCompatActivity activity) {
        getActionBar(activity).hide();
    }
}
