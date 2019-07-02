package com.libs.modle.listener.receiverListener;

import android.content.Context;

/**
 * @ author：mo
 * @ data：2018/10/25
 * @ 功能：短信接收监听
 */
public interface KOnSmsReceive {

    /**
     * 当接收到短信
     * @param context 上下文
     * @param from    来源号码（谁发过来的）
     * @param message 收到的内容
     */
    void onSmsReceive(Context context, String from, String message);
}
