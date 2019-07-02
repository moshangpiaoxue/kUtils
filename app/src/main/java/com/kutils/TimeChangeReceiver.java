package com.kutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.libs.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/2/15:17:01
 * @ 功能：
 */
public class TimeChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {//如果广播是每分钟发送一次的时间广播
            LogUtil.i("时间发生变化。。。 ");
        }
    }


}