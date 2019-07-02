package com.libs.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.libs.R;
import com.libs.utils.systemUtils.CameraUtil;
import com.libs.utils.systemUtils.storageUtil.SDCardUtil;
import com.libs.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2018/11/22
 * @ 功能：拍照或者从相册取图片
 */
public abstract class PhoneTakeDialog extends BaseDialog {
    public PhoneTakeDialog(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_phone_take;
    }

    @Override
    protected void doWhat(final Dialog dialog, View view) {
        //拍照
        view.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraUtil.isExistCamera()) {
                    dialog.dismiss();
                    actionPhoneTake();
                } else {
                    ToastUtil.showToast("没有找到拍照设备！");
                }

            }
        });
        //从相册选
        view.findViewById(R.id.btn_pick_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SDCardUtil.isEnable()) {
                    dialog.dismiss();
                    actionPhoneChoose();
                } else {
                    ToastUtil.showToast("SD卡不可用");
                }

            }
        });
        //取消
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected abstract void actionPhoneChoose();

    protected abstract void actionPhoneTake();


    @Override
    protected boolean setCanceledOnTouchOutsides() {
        return true;
    }
}
