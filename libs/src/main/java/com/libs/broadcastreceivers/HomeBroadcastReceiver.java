package com.libs.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.libs.k;
import com.libs.modle.listener.receiverListener.KOnHomeListener;


/**
 * @ author：mo
 * @ data：2018/12/14：17:52
 * @ 功能：监听home键
 */
public class HomeBroadcastReceiver {
    static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
    static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";
    private KOnHomeListener listener;
    private Receiver receiver;

    public HomeBroadcastReceiver(KOnHomeListener listener) {
        this.listener = listener;
        onCreate();
    }

    private void onCreate() {
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        receiver = new Receiver();
        k.app().registerReceiver(receiver, homeFilter);
    }

    /**
     * 释放资源
     */
    public void onDestroy() {
        if (receiver != null) {
            k.app().unregisterReceiver(receiver);
        }
    }

   public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                // android.intent.action.CLOSE_SYSTEM_DIALOGS
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    // 短按Home键
                    listener.onHomeTouch(1);
                } else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                    // 长按Home键 或者 activity切换键
                    listener.onHomeTouch(2);
                } else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                    // 锁屏
                    listener.onHomeTouch(3);
                } else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                    // samsung 长按Home键
                    listener.onHomeTouch(4);
                }
            }
        }
    }
}