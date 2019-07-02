package com.libs.view.popupWindow;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * author：mo
 * data：2017/11/16 0016
 * 功能：自定义PopupWindow，解决在7.0及其他版本中无法正常在设置的位置显示
 */

public class KPopupWindow extends PopupWindow {
    public KPopupWindow(View contentView) {
        super(contentView);
    }

    public KPopupWindow(View contentView, int width, int height) {
        this(contentView, width, height, false);
    }
    public KPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

}