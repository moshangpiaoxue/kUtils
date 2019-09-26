package com.libs.utils.appUtils.barUtils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.libs.k;
import com.libs.utils.colorsUtils.ColorUtils;
import com.libs.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/2/13:18:30
 * @ 功能：
 */
public class BarUtil {
    /**
     * 隐藏系统状态栏和虚拟按键
     *
     * @param view View 对象
     */
    public static void hideNavigationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            LogUtil.i("SDK(>=19)版本过低,不支持: API=" + Build.VERSION.SDK_INT);
        }
    }



}
