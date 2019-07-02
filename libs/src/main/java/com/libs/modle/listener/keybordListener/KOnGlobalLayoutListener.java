package com.libs.modle.listener.keybordListener;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;



/**
 * @ author：mo
 * @ data：2018/12/13：16:57
 * @ 功能：软键盘消失/显示监听
 * view.getViewTreeObserver().addOnGlobalLayoutListener(new OnKeyboardChangeListener(view, this));
 */
public class KOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private View mContainer;
    private KOnKeybordChangeListener mOnChangerListener;

    /**
     * @param view             view是editText的父布局
     * @param onChangeListener
     */
    public KOnGlobalLayoutListener(View view, KOnKeybordChangeListener onChangeListener) {
        mContainer = view;
        mOnChangerListener = onChangeListener;
    }

    @Override
    public void onGlobalLayout() {
        if (mOnChangerListener != null) {
            Rect rect = new Rect();
            mContainer.getWindowVisibleDisplayFrame(rect);
            int heightDifference = mContainer.getRootView().getHeight() - rect.bottom;
            if (heightDifference > 200) {
                mOnChangerListener.onKeyboardShow();
            } else {
                mOnChangerListener.onKeyboardHidden();
            }
        }
    }
}