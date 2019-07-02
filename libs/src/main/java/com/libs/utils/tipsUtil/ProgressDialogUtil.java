package com.libs.utils.tipsUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：等待进度条工具类
 */
public class ProgressDialogUtil {

    public static ProgressDialog progressLoading = null;

    public static void showProgress(Activity act, String tips) {
        if (progressLoading == null&&!act.isFinishing()) {
            progressLoading = ProgressDialog.show(act,
                    "", tips, true, false);
            progressLoading.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        cancelProgress();
                    }
                    return false;
                }
            });
        }

    }

    public static void cancelProgress() {
        if (progressLoading != null && progressLoading.isShowing()) {
            progressLoading.dismiss();
            progressLoading = null;
        }
    }

}
