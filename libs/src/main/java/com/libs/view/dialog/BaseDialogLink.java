package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.libs.R;
import com.libs.utils.viewUtil.ViewUtil;


/**
 * @ author：mo
 * @ data：2018/9/25：8:58
 * @ 功能：
 */
public class BaseDialogLink {
    private Activity mActivity;
    private int layoutId = 0;
    /** 缩放值 */
    private double mDialogScale = 0.8;
    /** 黑暗度/透明度 */
    private float mDimAmount = 0.4f;
    /** 点击屏幕或物理返回键是否消失 */
    private boolean isCancelable = false;
    /** 触摸屏幕不消失但是点击物理返回键是消失 */
    private boolean isCanceledOnTouchOutsides = false;
    /** dialog消失监听 */
    private DialogInterface.OnDismissListener onDismissListener;
    /** 监听系统返回键 */
    private DialogInterface.OnKeyListener onKeyListener;
    private Dialog mDialog;

    public BaseDialogLink(Activity mActivity, int layoutId) {
        this.mActivity = mActivity;
        this.layoutId = layoutId;
    }
    public static BaseDialogLink builder(Activity mActivity, int layoutId) {
        return new BaseDialogLink(mActivity, layoutId);
    }
    /** 设置布局id */
    public BaseDialogLink setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    /**
     * 设置dialog与屏幕之间的比例
     */
    public BaseDialogLink setDialogScale(double mDialogScale) {
        this.mDialogScale = mDialogScale;
        return this;
    }

    /**
     * 设置黑暗度（Dialog自身的黑暗度）
     */
    public BaseDialogLink setDimAmount(float mDimAmount) {
        this.mDimAmount = mDimAmount;
        return this;
    }

    /**
     * false=dialog弹出后会点击屏幕或物理返回键，dialog不消失
     * true=消失
     */
    public BaseDialogLink setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    /**
     * false=dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
     */
    public BaseDialogLink setCanceledOnTouchOutsides(boolean isCanceledOnTouchOutsides) {
        this.isCanceledOnTouchOutsides = isCanceledOnTouchOutsides;
        return this;
    }

    /**
     * 设置消失监听
     */
    public BaseDialogLink setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    /**
     * 监听系统返回键
     * 注意：onKey的return false的时候为不监听
     */
    public BaseDialogLink setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return this;
    }

    public interface CallBack {
        void callBack(Dialog dialog,View view);
    }

    public BaseDialogLink run(CallBack callBack) {

        if (layoutId == 0) {
            throw new UnsupportedOperationException("没有布局id，你想干啥！");
        }
        // 获取Dialog布局
        View view = ViewUtil.getView(mActivity, layoutId);
        // 定义Dialog布局和参数
        mDialog = new Dialog(mActivity, R.style.AlertDialogStyle);
        mDialog.setContentView(view);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (((WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() * mDialogScale);
        lp.dimAmount = mDimAmount;
        mDialog.getWindow().setAttributes(lp);

        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutsides);

        if (onDismissListener != null) {
            mDialog.setOnDismissListener(onDismissListener);
        }
        if (onKeyListener != null) {
            mDialog.setOnKeyListener(onKeyListener);
        }
        callBack.callBack(mDialog,view);
        return this;
    }


    /**
     * 开启
     */
    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 关闭
     */
    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog.cancel();
        }
    }
}
