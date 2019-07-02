package com.libs.modle.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.libs.k;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.ScreenUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



/**
 * @ author：mo
 * @ data：2019/1/10:14:47
 * @ 功能：输入法管理器
 */
public enum KInputMethodManager {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 获取输入法管理器
     */
    public InputMethodManager getInputMethodManager() {
        return (InputMethodManager) k.app().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    /**
     * 输入法是否显示：
     * 当输入法一次都没出现过的时候contentView值为0
     */
    public boolean isShow(Activity activity) {
        //获取当屏幕内容的高度
        int DecorViewHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        LogUtil.i("DecorViewHeight====" + DecorViewHeight +
                "\nScreenUtil===" + ScreenUtil.getScreenHeight() +
                "\nrect.top===" + rect.top +
                "\nrect.bottom===" + rect.bottom +
                "\ngetSoftButtonsBarHeight(activity)===" + getSoftButtonsBarHeight(activity));
        return DecorViewHeight != 0 && DecorViewHeight * 2 / 3 < rect.bottom + getSoftButtonsBarHeight(activity);
    }


    /**
     * 改变软键盘状态 如果开启则关闭，如果关闭则开启
     */
    public void changKeybordState() {
        if (getInputMethodManager() != null) {
//            关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的,下面两个方法作用相同
            getInputMethodManager().toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//            getInputMethodManager().toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 动态显示软键盘
     *
     * @param activity activity
     */
    public void openKeybord(final Activity activity, Boolean isShow) {
        openKeybord(activity, null, isShow);
    }

    public void openKeybord(View v, Boolean isShow) {
        openKeybord(null, v, isShow);
    }

    /**
     * @param activity
     * @param v
     * @param isShow
     * @ 避免输入法面板遮挡
     * <p>在 manifest.xml 中 activity 中设置</p>
     * <p>android:windowSoftInputMode="adjustPan"</p>
     */
    public void openKeybord(final Activity activity, View v, Boolean isShow) {
        InputMethodManager imm = getInputMethodManager();
        if (imm == null) {
            return;
        }
        if (v == null && activity == null) {
            return;
        }
        if (isShow) {
//            v.setFocusable(true);
//            v.setFocusableInTouchMode(true);
//            v.requestFocus();
            imm.showSoftInput(v != null ? v : activity.getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow((v != null ? v : activity.getWindow().getDecorView()).getWindowToken(), 0);
        }
    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(Boolean isShow, int height);
    }

    /**
     * 监听软键盘弹出/隐藏
     * 清单相应activity下必加：android:windowSoftInputMode="stateAlwaysHidden|adjustResize"
     *
     * @param activity
     * @param listener
     */
    public void listener(Activity activity, final OnSoftInputChangedListener listener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > ScreenUtil.getScreenHeight() / 4)) {
                    listener.onSoftInputChanged(true, 0);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > ScreenUtil.getScreenHeight() / 4)) {
                    listener.onSoftInputChanged(false, 0);
                }
            }
        });
    }

    /**
     * 监听软键盘弹出/隐藏
     * 清单相应activity下不能加：android:windowSoftInputMode="stateAlwaysHidden|adjustResize"
     * 界面销毁的时候必须注销监听
     *
     * @param activity
     * @param listener
     */
    public ViewTreeObserver.OnGlobalLayoutListener listener2(final Activity activity, final OnSoftInputChangedListener listener) {
        final View contentView = activity.findViewById(android.R.id.content);
        final int[] sContentViewInvisibleHeightPre = {getContentViewInvisibleHeight(activity)};
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    int height = getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre[0] != height) {
                        listener.onSoftInputChanged(height > 0 && ScreenUtil.getScreenHeight() / 3 > height, height);
                        sContentViewInvisibleHeightPre[0] = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        return onGlobalLayoutListener;
    }

    /**
     * 移除监听
     * onDestroy()
     *
     * @param activity
     * @param listener
     */
    public void removeListener2(Activity activity, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            activity.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    private int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        return contentView.getBottom() - r.bottom;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod(Activity activity, EditText editText) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0

            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (NoSuchMethodException e) {
                editText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
