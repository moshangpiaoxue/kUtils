package com.libs.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.libs.k;
import com.libs.modle.listener.receiverListener.KOnLockScreenListener;


/**
 * @ author：mo
 * @ data：2018/12/14
 * @ 功能：锁屏广播
 */
public class LockScreenBroadcastReceiver {
    private KOnLockScreenListener listener;
    private Receiver receiver;

    public LockScreenBroadcastReceiver(KOnLockScreenListener listener) {
        this.listener = listener;
        onCreat();
    }

    private void onCreat() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        receiver = new Receiver();
        k.app().registerReceiver(receiver, filter);
    }

    public void onDestroy() {
        if (receiver != null) {
            k.app().unregisterReceiver(receiver);
        }
    }

   public  class Receiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            // 开屏
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                listener.onScreenChange(false);
                // 锁屏
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                listener.onScreenChange(true);
                // 解锁
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                listener.onScreenChange(true);
            }
        }
    }
}
