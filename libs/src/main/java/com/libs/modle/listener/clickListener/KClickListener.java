package com.libs.modle.listener.clickListener;

import android.view.View;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：点击监听
 */

public class KClickListener {
    /**
     * 点击事件监听
     */
    public class KOnClickListener implements View.OnClickListener {
        //        事件源控件
        @Override
        public void onClick(View view) {

        }
    }

    /**
     * 长按点击事件监听
     * 返回值：该方法的返回值为一个boolean类型的变量，当返回true时，表示已经完整地处理了这个事件，
     * 并不希望其他的回调方法再次进行处理；当返回false时，表示并没有完全处理完该事件，更希望其他方法继续对其进行处理。
     */
    public class KOnLongClickListener implements View.OnLongClickListener {
        //        事件源控件
        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
