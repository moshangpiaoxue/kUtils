package com.libs.utils.systemUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.libs.k;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * author：mo
 * data：2017/11/4 0004
 * 功能： 软键盘工具类
 */

public class KeyBordUtil {
    private static int sContentViewInvisibleHeightPre;

    /**
     * 获取输入法管理器
     */
    public static InputMethodManager getInputMethodManager() {
        return (InputMethodManager) k.app().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static void isShowKeybord(final Activity activity, Boolean isShow) {
        isShowKeybord(activity, null, isShow);
    }

    public static void isShowKeybord(View v, Boolean isShow) {
        isShowKeybord(null, v, isShow);
    }

    /**
     * 设置输入法显示状态
     *
     * @param activity 当前活动
     * @param view     关联view 一般为editview
     * @param isShow   是否显示
     * @ 避免输入法面板遮挡
     * <p>在 manifest.xml 中 activity 中设置</p>
     * <p>android:windowSoftInputMode="adjustPan"</p>
     */
    public static void isShowKeybord(final Activity activity, View view, Boolean isShow) {
        InputMethodManager imm = getInputMethodManager();
        if (imm == null) {
            return;
        }
        if (view == null && activity == null) {
            return;
        }
        if (isShow) {
//            v.setFocusable(true);
//            v.setFocusableInTouchMode(true);
//            v.requestFocus();
            imm.showSoftInput(view != null ? view : activity.getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow((view != null ? view : activity.getWindow().getDecorView()).getWindowToken(), 0);
        }
    }

    /**
     * 禁掉系统软键盘
     */
    public static void hideSoftInputMethod(Activity activity, EditText editText) {
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




















    /**
     * 打开软键盘
     *
     * @param mContext
     * @param v        避免输入法面板遮挡
     *                 <p>在 manifest.xml 中 activity 中设置</p>
     *                 <p>android:windowSoftInputMode="adjustPan"</p>
     */
    public static void openKeybord(Context mContext, View v) {
        //第一种
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        v.requestFocus();
        if (imm != null) {
            boolean isOpen = imm.isActive();
            System.out.println(isOpen);
            if (!isOpen) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
//        InputMethodManager imm = (InputMethodManager) v.getContext().getApplicationContext()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        //第二种
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(v, InputMethodManager.RESULT_SHOWN);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        //第三种
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            mEditText.requestFocus();
//            imm.showSoftInput(mEditText, 0);
//        }
    }

    /**
     * 动态显示软键盘
     *
     * @param activity activity
     */
    public static void openKeybord(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 动态显示软键盘
     *
     * @param view 视图
     */
    public static void openKeybord(final View view) {
        InputMethodManager imm =
                (InputMethodManager) k.app().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     *
     * @param activity 上下文
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     *
     * @param view 任意view
     */
    public static void closeKeybord(View view) {
        if (null == view)
            return ;

        InputMethodManager inputManager = getInputMethodManager();
        // 即使当前焦点不在editText，也是可以隐藏的。
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 改变软键盘状态 如果开启则关闭，如果关闭则开启
     */
    public static void changKeybordState() {
        //得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) k.app().getSystemService(Context.INPUT_METHOD_SERVICE);
//        方法1
        //如果开启
        if (imm.isActive()) {
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        //方法2
//        if (imm == null){ return;}
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    private static int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        return contentView.getBottom() - r.bottom;
    }

    /**
     * 注册软键盘改变监听器
     *
     * @param activity activity
     * @param listener listener
     *                 return：自定义一个高度 一般是200-300，用来
     */
    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        final View contentView = activity.findViewById(android.R.id.content);
        sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    int height = getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre != height) {
                        listener.onSoftInputChanged(height);
                        sContentViewInvisibleHeightPre = height;
                    }
                }
            }
        });
    }

    /**
     * 添加软键盘弹出隐藏监听
     *
     * @param activity
     * @param listener true==显示  false==隐藏
     */
    public static void addKeyBordShowListener(final Activity activity, final KOnKeyBordShowListener listener) {
        addKeyBordShowListener(activity, ScreenUtil.getScreenHeight() / 3, listener);
    }

    /**
     * 添加软键盘弹出隐藏监听
     *
     * @param activity
     * @param keyHeight 判断高度
     * @param listener  true==显示  false==隐藏
     */
    public static void addKeyBordShowListener(final Activity activity, final int keyHeight, final KOnKeyBordShowListener listener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

                    listener.OnLayoutChangeListener(true);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    listener.OnLayoutChangeListener(false);

                }
            }
        });
    }

    /**
     * 修复软键盘内存泄漏
     * <p>在{@link Activity#onDestroy()}中使用</p>
     *
     * @param context context
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) {
                    continue;
                }
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) {
                    continue;
                }
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写 dispatchTouchEvent</p>
     * <p>参照以下注释代码</p>
     */
    public static void clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }

    public interface KOnKeyBordShowListener {
        void OnLayoutChangeListener(Boolean isShow);
    }
}
