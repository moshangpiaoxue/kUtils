package com.libs.modle.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.libs.R;
import com.libs.view.popupWindow.KPopupWindow;


/**
 * author：mo
 * data：2017/11/16 0016
 * 功能：pop模型
 */

public abstract class BasePopModle {
    /**
     *动画出现样式集合 0=从上到下 1=从下到上
     */
    public static int[] styles = {R.style.kpop_top_2_bottom_style, R.style.kpop_bottom_2_top_style};

    /**
     * 构造方法
     *
     * @param activity           上下文
     * @param layoutId           pop内容布局id
     * @param atLocationView     显示位置view,pop以此view为基点出现 上或者下
     * @param height             pop高度 0 自适应 1 胀满 其他为具体高度值
     * @param upOrDown           0=位于上面   从下往上弹，1=位于下面   从上往下弹
     * @param bgWindowAlpha_0_1  背景透明度：区间为0f-1f，0f为完全不透明，1f为完全透明
     * @param popBackgroundColor PopupWindow背景颜色可前缀透明度
     */
    public BasePopModle(Activity activity, int layoutId, View atLocationView, int height, float bgWindowAlpha_0_1, int popBackgroundColor
            ,
                        int upOrDown,int animType) {
        //获取内容view
        View rootView = getRootView(activity, layoutId);
        //载体view
        PopupWindow window = getWindowView(rootView, height, upOrDown, atLocationView, popBackgroundColor,animType);
        // 设置背景透明度
        Window bgWindow = getBgWindow(activity, window, bgWindowAlpha_0_1);
        //交互
        doWhat(rootView, window, bgWindow);

    }


    /**
     * 设置pop出现后背景透明度和pop消失后背景透明度
     *
     * @param activity      当前界面
     * @param window        出现的pop
     * @param bgWindowAlpha 背景透明度：区间为0f-1f，0f为完全不透明，1f为完全透明
     * @return Window  背景Window
     */
    private Window getBgWindow(Activity activity, PopupWindow window, float bgWindowAlpha) {
        final Window bgWindow = activity.getWindow();
        WindowManager.LayoutParams lp = bgWindow.getAttributes();
        lp.alpha = bgWindowAlpha;
        bgWindow.setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = bgWindow.getAttributes();
                lp.alpha = 1f;
                bgWindow.setAttributes(lp);
            }
        });
        return bgWindow;
    }

    /**
     * 获取WindowView
     *
     * @param rootView           根view
     * @param height             pop高度 0 自适应 1 胀满 其他为具体高度值
     * @param upOrDown           pop出现位置 0从下往上弹，1 从上往下弹
     * @param atLocationView     显示位置view,pop以此view为基点出现 上或者下
     * @param popBackgroundColor PopupWindow的int类型的背景颜色值 前缀透明度
     * @return PopupWindow
     */
    @SuppressLint("WrongConstant")
    private PopupWindow getWindowView(View rootView, int height, int upOrDown, View atLocationView, int popBackgroundColor, int animType) {
        PopupWindow window;
        //创建window
        if (height == 0) {
            window = new KPopupWindow(rootView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        } else if (height == 1) {
            window = new KPopupWindow(rootView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        } else {
            window = new KPopupWindow(rootView, WindowManager.LayoutParams.MATCH_PARENT, height);
        }

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        //设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(popBackgroundColor));
        window.setAnimationStyle(styles[animType]); // 设置popWindow的显示和消失动画
        //pop显示位置  atLocationView上面
        if (upOrDown == 0) {
            //解决PopupWindow含有输入框时，点击输入框，软键盘可能会挡住PopupWindow 顺序不能变
            window.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            window.setAnimationStyle(styles[animType]); // 设置popWindow的显示和消失动画
            window.showAtLocation(atLocationView, Gravity.BOTTOM, 0, 0);

            //pop显示位置  atLocationView下面
        } else if (upOrDown == 1) {

//            window.setAnimationStyle(styles[animType]); // 设置popWindow的显示和消失动画
            window.showAsDropDown(atLocationView);
        }
        return window;
    }

    /**
     * 获取内容view
     *
     * @param activity 环境
     * @param layoutId 布局id
     * @return view
     */
    private View getRootView(Activity activity, int layoutId) {
        return ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);
    }

    /**
     * 交互操作
     *
     * @param rootView 内容view 根view
     * @param window   popview
     * @param bgWindow 背景window
     */
    protected abstract void doWhat(View rootView, PopupWindow window, Window bgWindow);
}
