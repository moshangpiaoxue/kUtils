package com.libs.modle.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * @ author：mo
 * @ data：2018/1/5 0005
 * @ 功能：根据参照View的位置大小来决定一个提示悬浮窗的位置。
 */

public class PromptUtil {
    // targetView出现在referenceView的什么位置
    public enum ENUM {
        TOP, BOTTOM
    }

    private static Context mContext;
    // 悬浮窗
    private static ImageView mTargetView;
    // 参照View ，悬浮窗根据参照View的位置大小来决定其位置
    private static View mReferenceView;
    private static ENUM mAnEnum;


    public static void show(Context context, int res, View referenceView, ENUM anEnum) {
        mContext = context;
        mTargetView = new ImageView(context);
        mTargetView.setImageResource(res);
        mReferenceView = referenceView;
        mAnEnum = anEnum;
        waitViewMeasure(mReferenceView);
    }

    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startShow();
        }
    };

    private static void startShow() {
        final WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSLUCENT; // 图片之外的其他地方透明

        // 大小
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 位置
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        // 参照View的x坐标
        float referenceX = mReferenceView.getLeft();
        // 参照项View的Y坐标
        float referenceY = mReferenceView.getTop();
        // 参照项View的长
        int referenceW = mReferenceView.getMeasuredWidth();
        // 参照项View的宽
        int referenceH = mReferenceView.getMeasuredHeight();


        switch (mAnEnum) {
            case TOP:
                layoutParams.x = (int) referenceX;
                layoutParams.y = (int) referenceY - mTargetView.getDrawable().getIntrinsicHeight();
                break;
            case BOTTOM:
                layoutParams.x = (int) referenceX;
                layoutParams.y = (int) referenceY + referenceH;
                break;
            default:
                break;
        }

        //  适应状态栏高度
        layoutParams.y -= getStatusHeight(mContext);

        // 透明背景
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = 0.9f;
        ((Activity) mContext).getWindow().setAttributes(lp);

        // 设置触摸监听,移除悬浮窗和内存.恢复背景透明度
        mTargetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 移除悬浮窗
                wm.removeView(mTargetView);
                // 恢复背景
                WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) mContext).getWindow().setAttributes(lp);
                // 移除内存
                mContext = null;
                mTargetView = null;
                mReferenceView = null;
                mAnEnum = null;

                return false;
            }
        });
        // 添加悬浮窗
        wm.addView(mTargetView, layoutParams);
    }

    // 参照View在一段时间后才能获取x，y，width和height
    private static void waitViewMeasure(final View referenceView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (referenceView.getMeasuredWidth() == 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(101);

            }
        }).start();
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


}
