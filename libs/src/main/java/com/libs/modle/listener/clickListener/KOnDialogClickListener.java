package com.libs.modle.listener.clickListener;

import android.content.DialogInterface;

/**
 * @ author：mo
 * @ data：2017/1/29：14:54
 * @ 功能：Dialog点击接口
 */
public interface KOnDialogClickListener {
    /**
     * 同意、确认、知道了等肯定的选项
     *
     * @param dialog dialog
     * @param which  选中的位置
     */
    void onSure(DialogInterface dialog, int which);

    /**
     * 不同意、不确认、不知道等否定的选项
     *
     * @param dialog dialog
     * @param which  选中的位置
     */
    void onCancel(DialogInterface dialog, int which);
}
