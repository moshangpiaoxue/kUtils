package com.libs.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libs.R;
import com.libs.modle.listener.clickListener.KOnItemClickListener;
import com.libs.utils.dataUtil.StringUtil;

// new IosAlertDialog(mActivity).builder()
//         //                .setTitle("ssss")
//         //                .setMsg("干啥")
//         .setCoustomView(ViewUtil.getView(mActivity, com.libs.R.layout.dialog_loading))
//         .setLeftTextView("知道了", new KOnItemClickListenerImpl() {
//@Override
//public void onItemClick(View view, int position) {
//        super.onItemClick(view, position);
//        showToast("gansha");
//        }
//        })
//        //                .setRightTextView("不干啥", new KOnItemClickListenerImpl() {
//        //                    @Override
//        //                    public void onItemClick(View view, int position) {
//        //                        super.onItemClick(view, position);
//        //                        showToast("滚");
//        //                    }
//        //                })
//        .show();
public class IosAlertDialog {
    private Context context;
    private Dialog dialog;
    private View view;
    private String title;
    private String msg;
    private int msgViewGravity = -1;
    /** 点击手机返回按键是否允许对话框消失或者弹出后会点击屏幕，false不消失,true消失；点击物理返回键dialog消失 */
    private boolean cancelAble = false;
    /** 点击对话框外部区域是否允许对话框消失或者说点击屏幕或物理返回键，false不消失,true消失。 */
    private boolean canceledOnTouchOutside = false;
    private String leftTxt;
    private OnClickListener leftListener;
    private String rightTxt;
    private OnClickListener rightListener;
    private DialogInterface.OnDismissListener onDismissListener;
    private DialogInterface.OnKeyListener onKeyListener;
    private View coustomView = null;

    public IosAlertDialog(Context context) {
        this.context = context;
    }

    public IosAlertDialog builder() {
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view = LayoutInflater.from(context).inflate(R.layout.dialog_ios, null));
        // 调整dialog背景大小
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) ((((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getWidth() * 0.8);
        return this;
    }

    public IosAlertDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public IosAlertDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public IosAlertDialog setMsgGravity(int gravity) {
        this.msgViewGravity = gravity;
        //        msgView.setGravity(gravity);
        return this;
    }


    public IosAlertDialog setCancelable(boolean cancel) {
        this.cancelAble = cancel;
        return this;
    }

    public IosAlertDialog setLeftTextView(String leftTxt, OnClickListener listener) {
        this.leftTxt = leftTxt;
        this.leftListener = listener;
        return this;
    }

    public IosAlertDialog setRightTextView(String rightTxt, OnClickListener listener) {
        this.rightTxt = rightTxt;
        this.rightListener = listener;
        return this;
    }

    public IosAlertDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.onDismissListener = dismissListener;
        return this;
    }


    public IosAlertDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return this;
    }

    public IosAlertDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public IosAlertDialog setCoustomView(View coustomView) {
        this.coustomView = coustomView;
        return this;
    }

    private void setLayout() {
        dialog.setCancelable(cancelAble);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        TextView titleView = (TextView) view.findViewById(R.id.tv_dialog_ios_title);
        TextView msgView = (TextView) view.findViewById(R.id.tv_dialog_ios_msg);

        TextView tv_dialog_ios_left = (TextView) view.findViewById(R.id.tv_dialog_ios_left);
        TextView tv_dialog_ios_right = (TextView) view.findViewById(R.id.tv_dialog_ios_right);
        TextView tv_dialog_ios_line = (TextView) view.findViewById(R.id.tv_dialog_ios_line);


        if (coustomView != null) {
            LinearLayout ll_dialog_ios = view.findViewById(R.id.ll_dialog_ios);
            ll_dialog_ios.addView(coustomView, 1);
        }

        if (StringUtil.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isEmpty(msg)) {
            msgView.setVisibility(View.GONE);
        } else {
            msgView.setText(msg);
            msgView.setVisibility(View.VISIBLE);
        }
        if (msgViewGravity != -1) {
            msgView.setGravity(msgViewGravity);
        }
        if (StringUtil.isEmpty(leftTxt)) {
            tv_dialog_ios_left.setVisibility(View.GONE);
        } else {
            tv_dialog_ios_left.setText(leftTxt);
            tv_dialog_ios_left.setVisibility(View.VISIBLE);
        }

        if (StringUtil.isEmpty(rightTxt)) {
            tv_dialog_ios_right.setVisibility(View.GONE);
        } else {
            tv_dialog_ios_right.setText(rightTxt);
            tv_dialog_ios_right.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isEmpty(leftTxt) || StringUtil.isEmpty(rightTxt)) {
            tv_dialog_ios_line.setVisibility(View.GONE);
        } else {
            tv_dialog_ios_line.setVisibility(View.VISIBLE);
        }
        tv_dialog_ios_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (leftListener != null) {
                    leftListener.onClick(v);
                }
            }
        });
        tv_dialog_ios_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (rightListener != null) {
                    rightListener.onClick(v);
                }
            }
        });
        if (onDismissListener != null) {
            dialog.setOnDismissListener(onDismissListener);
        }
        if (onKeyListener != null) {
            dialog.setOnKeyListener(onKeyListener);
        }


        //        if (!showPosBtn && !showNegBtn) {
        //            tv_dialog_ios_right.setText("确定");
        //            tv_dialog_ios_right.setVisibility(View.VISIBLE);
        //            tv_dialog_ios_right.setBackgroundResource(R.drawable.iosdialog_single_selector);
        //            tv_dialog_ios_right.setOnClickListener(new OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    dialog.dismiss();
        //                }
        //            });
        //        }
        //
        //        if (showPosBtn && showNegBtn) {
        //            tv_dialog_ios_right.setVisibility(View.VISIBLE);
        //            tv_dialog_ios_right.setBackgroundResource(R.drawable.iosdialog_right_selector);
        //            tv_dialog_ios_left.setVisibility(View.VISIBLE);
        //            tv_dialog_ios_left.setBackgroundResource(R.drawable.iosdialog_left_selector);
        //        }
        //
        //        if (showPosBtn && !showNegBtn) {
        //            tv_dialog_ios_right.setVisibility(View.VISIBLE);
        //            tv_dialog_ios_right.setBackgroundResource(R.drawable.iosdialog_single_selector);
        //        }
        //
        //        if (!showPosBtn && showNegBtn) {
        //            tv_dialog_ios_left.setVisibility(View.VISIBLE);
        //            tv_dialog_ios_left.setBackgroundResource(R.drawable.iosdialog_single_selector);
        //        }
    }

    public void show() {
        boolean showing = dialog.isShowing();
        if (!showing) {
            setLayout();
            dialog.show();
        }
    }

    public void dismiss() {
        boolean showing = dialog.isShowing();
        if (showing && dialog != null) {
            dialog.dismiss();
        }
    }
}
