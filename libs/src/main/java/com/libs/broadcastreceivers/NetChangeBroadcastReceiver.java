package com.libs.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.libs.k;
import com.libs.modle.listener.receiverListener.KOnNetChangeListener;
import com.libs.modle.manager.KConnectivityManager;


/**
 * @ author：mo
 * @ data：2018/10/25
 * @ 功能：网络变化广播接收器
 */
public class NetChangeBroadcastReceiver {
    public KOnNetChangeListener mNetChangeListener;
    private Receiver receiver;

    public NetChangeBroadcastReceiver(KOnNetChangeListener mNetChangeListener) {
        this.mNetChangeListener = mNetChangeListener;
        onCreate();
    }

    private void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new Receiver();
        k.app().registerReceiver(receiver, filter);
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
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mNetChangeListener.onNetChange(KConnectivityManager.INSTANCE.getNetworkType());
            }
        }
    }

}
