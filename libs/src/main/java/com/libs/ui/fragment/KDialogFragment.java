package com.libs.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * @ author：mo
 * @ data：2018/4/17 0017
 * @ 功能： https://blog.csdn.net/zhyh1986/article/details/48655885
 * https://blog.csdn.net/angcyo/article/details/50613084
 * mWindow = getDialog().getWindow();
 * //无标题
 * mWindow.requestFeature(Window.FEATURE_NO_TITLE);//必须放在setContextView之前调用
 * rootView = (ViewGroup) inflater.inflate(R.layout.rsen_base_dialog_fragment_layout,
 * (ViewGroup) mWindow.findViewById(android.R.id.content));
 * //透明状态栏
 * mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
 * //退出,进入动画
 * mWindow.setWindowAnimations(getAnimStyles());
 * //清理背景变暗
 * mWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
 * //点击window外的区域 是否消失
 * getDialog().setCanceledOnTouchOutside(canCanceledOnOutside());
 * //是否可以取消,会影响上面那条属性
 * setCancelable(canCancelable());
 * //window外可以点击,不拦截窗口外的事件
 * mWindow.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
 * //设置背景颜色,只有设置了这个属性,宽度才能全屏MATCH_PARENT
 * mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 * WindowManager.LayoutParams mWindowAttributes = mWindow.getAttributes();
 * mWindowAttributes.width = getWindowWidth();//这个属性需要配合透明背景颜色,才会真正的 MATCH_PARENT
 * mWindowAttributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
 * 设置背景透明度
 * WindowManager.LayoutParams windowParams = window.getAttributes();
 * windowParams.dimAmount = 0.1f;
 * window.setAttributes(windowParams);
 * //gravity
 * mWindowAttributes.gravity = getGravity();
 * mWindow.setAttributes(mWindowAttributes);
 */

public abstract class KDialogFragment extends DialogFragment {
    protected Activity mActivity;
    protected View mView;
    protected Dialog mDialog;
    protected Window mWindow;
    protected DialogFragmentCallBack callBack;

    public interface DialogFragmentCallBack {
        void onSure(KDialogFragment dialog);

        void onCancel(KDialogFragment dialog);

        void onDialogDismiss();
    }


    //    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setupWindow(getDialog().getWindow());
//    }
//    private void setupWindow(Window window) {
//        if (window != null) {
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.gravity = Gravity.CENTER;
//            lp.dimAmount = 0;
//            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//            window.setAttributes(lp);
//            window.setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
//            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }
//    }
    public void setDialogFragmentCallBack(DialogFragmentCallBack callback) {
        this.callBack = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mDialog = getDialog();
        mWindow = mDialog.getWindow();
        //去除默认的标题，全部使用自定义的布局，增强通用性 此方法必须在生成view之前调用
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //有边框 类似于pading50的效果，为了通用，让它全部占满
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mView = inflater.inflate(getLayoutId(), container, false);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
//        }
//        return dialog;
//    }


    /**
     * 点击外部是否关闭,
     */
    @Override
    public boolean isCancelable() {
        return super.isCancelable();
    }


    @Override
    public void onStart() {
        super.onStart();
        //设置点击外部是否消失
        mDialog.setCancelable(isCancelable());
        //设置点击系统返回键是否消失
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return !isCancelable();
                }
                return !isCancelable();
            }
        });
        doWhat(mDialog, mWindow, mView);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (callBack != null) {
            callBack.onDialogDismiss();
        }
    }

    /**
     * 做什么
     *
     * @param dialog
     * @param view
     */
    protected abstract void doWhat(Dialog dialog, Window window, View view);

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();
    /////////////////////////////////////////以下方法最好在doWhat里调用//////////////////////////////////////////////////////////////

    /**
     * 设置window背景的透明度
     *
     * @param f
     */
    public void setBgWindowAlpha(Float f) {
        WindowManager.LayoutParams windowParams = mWindow.getAttributes();
        windowParams.dimAmount = f;
        mWindow.setAttributes(windowParams);
    }

    /**
     * 设置window的宽高   这个方法只能在 onStart 之后调用
     * window.setLayout(ScreenUtil.getScreenWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
     *
     * @param w
     * @param h
     */
    public void setLayoutSize(int w, int h) {
        mWindow.setLayout(w, h);
    }


}
