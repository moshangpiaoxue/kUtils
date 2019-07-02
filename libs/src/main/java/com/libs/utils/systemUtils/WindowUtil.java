package com.libs.utils.systemUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * @ author：mo
 * @ data：2018/1/29：16:32
 * @ 功能：WindowManager相关
 */
public class WindowUtil {
    public static Window getWindow(Activity mActivity) {
        return mActivity.getWindow();
    }

    public static WindowManager getWindowManager(Activity mActivity) {
        return getWindow(mActivity).getWindowManager();
    }

    /**
     * 设置窗口属性：这个方法可以把当前activity改成窗口模式，注意，不是dialog的样子
     *
     * @param mActivity 载体
     * @param BgResId   背景id
     * @param mHeight   高
     * @param mWidth    宽
     * @param mGravity  排版
     * @param xOffset   X轴偏移量
     * @param yOffset   Y轴偏移量
     * @param mAlpha    透明度
     */
    public static void setWindowAttribute(Activity mActivity, int BgResId, int mHeight, int mWidth,
                                          int mGravity, int xOffset, int yOffset, Float mAlpha) {
        Window w = getWindow(mActivity);
        //设置窗口背景
        w.setBackgroundDrawableResource(BgResId);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        layoutParams.height = mHeight;
        layoutParams.width = mWidth;
        layoutParams.gravity = mGravity;
        layoutParams.x = xOffset;//距离Gravity属性的距离
        layoutParams.y = yOffset;
        layoutParams.alpha = mAlpha;//0:完全透明，1：不透明
        w.setAttributes(layoutParams);
    }


    public interface WindowManafeCallBack {
        void back(WindowManager mWindowManager, WindowManager.LayoutParams mWindowLayoutParams);
    }

    public static void creatWindow(Activity activity, int margTop, WindowManafeCallBack callBack) {
        //Activity中的方法,得到窗口管理器
        WindowManager mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        //设置悬浮窗布局属性
        WindowManager.LayoutParams mWindowLayoutParams = new WindowManager.LayoutParams();
        //设置类型,具体有哪些值可取在后面附上
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置行为选项,具体有哪些值可取在后面附上
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置悬浮窗的显示位置
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        //        //设置悬浮窗的横竖屏,会影响屏幕方向,只要悬浮窗不消失,屏幕方向就会一直保持,可以强制屏幕横屏或竖屏
        //        mWindowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        //设置 x 轴的偏移量
        mWindowLayoutParams.x = 0;
        //设置 y 轴的偏移量
        mWindowLayoutParams.y = margTop;
        //如果悬浮窗图片为透明图片,需要设置该参数为 PixelFormat.RGBA_8888
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        //设置悬浮窗的宽度
        mWindowLayoutParams.width = ScreenUtil.getScreenWidth();
        //设置悬浮窗的高度
        mWindowLayoutParams.height = 98;
        callBack.back(mWindowManager, mWindowLayoutParams);
    }

    /**
     * 设置window宽高，（主要是为了解决当activity设置为dialog样式时，布局设置MATCH_PARENT失效的问题）
     */
    public static void setParams(Activity mActivity) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        if (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            TypedArray a = mActivity.obtainStyledAttributes(new int[]{android.R.attr.layout_width});
            try {
                params.width = a.getLayoutDimension(0, ViewGroup.LayoutParams.MATCH_PARENT);
            } finally {
                a.recycle();
            }
        }
        mActivity.getWindow().setAttributes(params);
    }

}
