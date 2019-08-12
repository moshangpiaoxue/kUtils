package com.libs.utils.tipsUtil;

import android.app.Activity;
import android.app.ProgressDialog;

import com.libs.utils.logUtils.LogUtil;

/**
 * @ author：mo
 * @ data：2019/8/9:16:36
 * @ 功能：
 */
public class KProgressDialog {
    private static ProgressDialog progressDialog = null;

    /**
     * 展示加载进度条,无标题
     */
    public static void showProgressDialog(Activity context) {
        try {
            showProgressDialog(context, null, "");
        } catch (Exception e) {
            LogUtil.i("showProgressDialog  showProgressDialog(Context context, null, context.getResources().getString(stringResId));");
        }
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param dialogMessage
     */
    public void showProgressDialog(Activity context, String dialogMessage) {
        showProgressDialog(context, null, dialogMessage);
    }

    /**
     * 展示加载进度条
     *
     * @param title Title 标题
     * @param msg   Message 信息
     */
    public static void showProgressDialog(final Activity context, final String title, final String msg) {
        if (context == null) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                if (progressDialog.isShowing() == true) {
                    progressDialog.dismiss();
                }
                if (title != null && !"".equals(title.trim())) {
                    progressDialog.setTitle(title);
                }
                if (msg != null && !"".equals(msg.trim())) {
                    progressDialog.setMessage(msg);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }


    /**
     * 隐藏加载进度
     */
    public static void dismissProgressDialog(Activity context) {
        if (context == null || progressDialog == null || progressDialog.isShowing() == false) {
            return;
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }
}
