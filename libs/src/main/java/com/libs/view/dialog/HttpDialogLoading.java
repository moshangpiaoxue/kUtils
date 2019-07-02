package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.libs.R;
import com.libs.utils.dataUtil.StringUtil;


/**
 * @ author：mo
 * @ data：2019/5/13:9:05
 * @ 功能：
 */
public enum HttpDialogLoading {
    /**
     * 单例
     */
    INSTANCE;
    private float dialogDim = 0.3f;
    private BaseDialog loadingDialog;

    public void show(Activity mActivity, final String tips) {
        if (loadingDialog == null) {
            loadingDialog = new BaseDialog(mActivity) {
                @Override
                protected int getLayoutId() {
                    return R.layout.dialog_loading;
                }

                @Override
                protected void doWhat(Dialog dialog, View view) {

                    TextView tv_dialog_loading = view.findViewById(R.id.tv_dialog_loading);
                    if (StringUtil.isEmpty(tips)) {
                        tv_dialog_loading.setVisibility(View.GONE);
                    } else {
                        tv_dialog_loading.setVisibility(View.VISIBLE);
                        tv_dialog_loading.setText(tips);
                    }
                }

                @Override
                protected float setDimAmount() {
                    return dialogDim;
                }
            };
        }
        if (!mActivity.isFinishing()) {
            loadingDialog.show();
        }
    }

    public void setDialogDim(float dialogDim) {
        this.dialogDim = dialogDim;
    }

    public void dismiss() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }
}
