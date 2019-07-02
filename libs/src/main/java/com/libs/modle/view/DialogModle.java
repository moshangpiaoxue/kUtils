package com.libs.modle.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.libs.modle.listener.clickListener.KOnDialogClickListener;


/**
 * author：mo
 * data：2017/11/14 0014
 * 功能：Dialog模型
 */

public class DialogModle {

    /**
     * 退出AlertDialog
     * 点击外部可销售
     *
     * @param activity 环境
     * @param isFinsh  点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialogSignOut(Activity activity, final Boolean isFinsh, final KOnDialogClickListener callBack) {
        showDialog(activity, "提示", "确认退出吗", "确认", "取消", isFinsh, callBack);
    }

    /**
     * 提示AlertDialog
     *
     * @param activity 环境
     * @param context  提示文本
     * @param isFinsh  点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialogPropmt(Activity activity, String context, final Boolean isFinsh, final KOnDialogClickListener callBack) {
        showDialog(activity, "提示", context, "知道了", "", isFinsh, callBack);
    }

    /**
     * 选择AlertDialog
     *
     * @param activity 环境
     * @param title    title
     * @param content  提示文本：您确定要删除此评论
     * @param isFinsh  点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialog(Activity activity, String title, String content, final Boolean isFinsh, final KOnDialogClickListener callBack) {
        showDialog(activity, title, content, "确定", "取消", isFinsh, callBack);
    }

    /**
     * 选择AlertDialog
     *
     * @param activity    环境
     * @param contentView title
     * @param contentView 提示view
     * @param isFinsh     点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialog(Activity activity, String title, View contentView, final Boolean isFinsh, final KOnDialogClickListener callBack) {
        showDialog(activity, title, contentView, "确定", "取消", isFinsh, callBack);
    }

    /**
     * 自定义AlertDialog
     *
     * @param activity        环境
     * @param title           标题
     * @param content         文本
     * @param rightButtonText 右面按钮的文本 一般是肯定
     * @param leftButtonText  左面按钮的文本 一般是否定
     * @param isFinsh         点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialog(final Activity activity, String title, String content, String rightButtonText, String leftButtonText, final Boolean isFinsh,
                                  final KOnDialogClickListener callBack) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //点击外部、系统返回键的时候是否自动关闭Dialog：setCanceledOnTouchOutside(false)按对话框以外的地方不起作用。按返回键还起作用
        builder.setCancelable(isFinsh)
                //标题
                .setTitle(title)
                //内容文本
                .setMessage(content)
                //右边按钮
                .setPositiveButton(rightButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callBack != null) {
                            callBack.onSure(dialog, which);
                        }
                    }
                })
                //左边按钮
                .setNegativeButton(leftButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callBack != null) {
                            callBack.onCancel(dialog, which);
                        }

                    }
                })
                //创建
                .create()
                //展示
                .show();
    }

    /**
     * 自定义AlertDialog
     *
     * @param activity        环境
     * @param title           标题
     * @param contentView     文本view
     * @param rightButtonText 右面按钮的文本 一般是肯定
     * @param leftButtonText  左面按钮的文本 一般是否定
     * @param isFinsh         点击外部、系统返回键的时候是否自动关闭Dialog
     * @param callBack
     */
    public static void showDialog(final Activity activity, String title, View contentView, String rightButtonText, String leftButtonText, final Boolean isFinsh,
                                  final KOnDialogClickListener callBack) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //点击外部、系统返回键的时候是否自动关闭Dialog：setCanceledOnTouchOutside(false)按对话框以外的地方不起作用。按返回键还起作用
        builder.setCancelable(isFinsh)
                //标题
                .setTitle(title)
                //内容view
                .setView(contentView)
                //右边按钮
                .setPositiveButton(rightButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callBack != null) {
                            callBack.onSure(dialog, which);
                        }
                    }
                })
                //左边按钮
                .setNegativeButton(leftButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callBack != null) {
                            callBack.onCancel(dialog, which);
                        }

                    }
                })
                //创建
                .create()
                //展示
                .show();
    }
}
