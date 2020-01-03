package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.libs.R;

/**
 * @ author：mo
 * @ data：2020/1/3:11:01
 * @ 功能：进度圈dialog
 */
public class LoadingDialog extends BaseDialog {
    private TextView tv_loading_dialog;

    public LoadingDialog(Activity mActivity) {
        super(mActivity);
    }
    public LoadingDialog(Activity mActivity,String tips) {
        super(mActivity);
        tv_loading_dialog.setText(tips);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.loading_dialog;
    }

    @Override
    protected void doWhat(Dialog dialog, View view) {
        tv_loading_dialog=view.findViewById(R.id.tv_loading_dialog);
    }
}
