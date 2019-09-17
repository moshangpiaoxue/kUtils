package com.libs.service.keepLive.doubleService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.ServiceUtil;


/**
 * @ author：mo
 * @ data：2019/2/15:18:32
 * @ 功能：双服务+广播保活 之 广播
 */
public class LiveBroadcast extends BroadcastReceiver {
    private boolean isServiceRunning = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {//如果广播是每分钟发送一次的时间广播
            LogUtil.i( "时间变化了");
            isServiceRunning = ServiceUtil.isRuning(LiveServiceOne.class);
            if (!isServiceRunning) {
                Intent i = new Intent(context, LiveServiceOne.class);
                context.startService(i);
            }
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            LogUtil.i( "屏幕解锁了");
            isServiceRunning = ServiceUtil.isRuning(LiveServiceOne.class);
            if (!isServiceRunning) {
                Intent i = new Intent(context, LiveServiceOne.class);
                context.startService(i);
            }
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            LogUtil.i( "屏幕关闭了");
            isServiceRunning = ServiceUtil.isRuning(LiveServiceOne.class);
            if (!isServiceRunning) {
                Intent i = new Intent(context, LiveServiceOne.class);
                context.startService(i);
            }
        }
    }
}
