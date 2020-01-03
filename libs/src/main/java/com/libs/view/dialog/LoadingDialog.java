package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.libs.R;

/**
 * @ author：mo
 * @ data：2020/1/3:11:01
 * @ 功能：进度圈dialog
 */
public class LoadingDialog extends BaseDialog {
    public LoadingDialog(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.loading_dialog;
    }

    @Override
    protected void doWhat(Dialog dialog, View view) {

    }
}
