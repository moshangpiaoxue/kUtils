package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.KeyEvent;
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
public abstract class BaseDialog {
    protected Activity mActivity;
    protected Dialog dialog;
    private Display display;

    public BaseDialog(Activity mActivity) {
        this.mActivity = mActivity;
        WindowManager windowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        // 获取Dialog布局
        View view = ViewUtil.getView(mActivity, getLayoutId());
        // 定义Dialog布局和参数
        dialog = new Dialog(mActivity, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * setDialogScale());
        lp.dimAmount = setDimAmount();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCancelable(setCancelable());
        dialog.setCanceledOnTouchOutside(setCanceledOnTouchOutsides());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                OnDismissListener(dialog);
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return OnKeyListener(dialog, keyCode, event);
            }
        });
        doWhat(dialog, view);
    }

    /**
     * 设置黑暗度（Dialog自身的黑暗度）
     */
    protected float setDimAmount() {
        return 0.4f;
    }

    protected abstract int getLayoutId();

    /**
     * 设置dialog与屏幕之间的比例
     */
    protected double setDialogScale() {
        return 0.8;
    }

    /**
     * false=dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
     */
    protected boolean setCanceledOnTouchOutsides() {
        return false;
    }

    /**
     * 监听系统返回键
     */
    protected Boolean OnKeyListener(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * dialog消失监听
     */
    protected void OnDismissListener(DialogInterface dialog) {
    }

    /**
     * false=dialog弹出后会点击屏幕或物理返回键，dialog不消失
     * true=消失
     */
    protected boolean setCancelable() {
        return false;
    }

    protected abstract void doWhat(Dialog dialog, View view);

    /**
     * 开启
     */
    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 关闭
     */
    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
    }
}
