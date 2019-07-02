package com.libs.view.popupWindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * author：mo
 * data：2017/11/16 0016
 */

public class KPopupWindow2 extends PopupWindow {
    /**
     * 环境
     */
    private Activity activity;
    /**
     * 根view 要显示的布局view
     */
    private View mRootView = null;
    /**
     * 布局id
     */
    private int layoutId;
    /**
     * 宽 默认自适应
     */
    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * 高 默认自适应
     */
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private View atLocationView;
    private int height;
    private int upOrDown;
    private int mAnimationStyle = -1;
    /**
     * 背景灰度  0-1  1表示全透明
     */
    private float mAlpha = 1f;
    /**
     * 点击外部消失 默认true（消失）
     */
    private boolean isTouchOutsideDismiss = true;

    public KPopupWindow2(Activity activity) {
        super(activity);
    }

    public KPopupWindow2(View contentView) {
        super(contentView);
    }

    public KPopupWindow2(View contentView, int width, int height) {
        this(contentView, width, height, false);
    }

    public KPopupWindow2(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public void initView() {
        setContentView(mRootView);
        setHeight(mHeight);
        setWidth(mWidth);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        setFocusable(isTouchOutsideDismiss);
        setBackgroundDrawable(new ColorDrawable(0xb111111));//设置背景
        if (mAnimationStyle != -1) {
            setAnimationStyle(mAnimationStyle);
        }

    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT < 24) {
            showAsDropDown(anchor);
        } else {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(activity.getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1]);
        }
    }


    public static class Builder {
        private KPopupWindow2 mPopupWindow;

        public static Builder build(Activity activity, View rootView) {
            return new Builder(activity, rootView);
        }

        private Builder(Activity activity, View rootView) {
            mPopupWindow = new KPopupWindow2(rootView);
            mPopupWindow.activity = activity;
            mPopupWindow.mRootView = rootView;
        }

        public Builder setWidth(int width) {
            mPopupWindow.mWidth = width;
            return this;
        }

        public Builder setHight(int hight) {
            mPopupWindow.mHeight = hight;
            return this;
        }

        public Builder setatLocationView(View atLocationView) {
            mPopupWindow.atLocationView = atLocationView;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            mPopupWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public Builder setAlpha(float alpha) {
            mPopupWindow.mAlpha = alpha;
            return this;
        }

        public Builder setOutsideTouchDismiss(boolean dismiss) {
            mPopupWindow.isTouchOutsideDismiss = dismiss;
            return this;
        }

        public KPopupWindow2 getPopupWindow() {
            mPopupWindow.initView();
            return mPopupWindow;
        }
    }
}