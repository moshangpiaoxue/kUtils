package com.libs.modle.service.keepLive;

import android.content.Intent;
import android.content.IntentFilter;

import com.libs.k;
import com.libs.modle.service.keepLive.doubleService.LiveBroadcast;
import com.libs.modle.service.keepLive.doubleService.LiveServiceOne;


/**
 * @ author：mo
 * @ data：2019/2/16:13:54
 * @ 功能：双服务+广播保活 之 管理器
 */
public class KeepLiveManager {
    /**
    * 开启双服务+广播保活(别忘了在清单里注册service)
    */
    public static void startDoubleServiceLive(){
        //开启系统时间广播(动态注册,不能静态注册)
        //部分机型会屏蔽时间广播
        IntentFilter intentFilter = new IntentFilter();
        //系统时间，每分钟发送一次
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        //屏幕打开（解锁）
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //屏幕关闭
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        LiveBroadcast myBroadcast = new LiveBroadcast();
        k.app().registerReceiver(myBroadcast, intentFilter);

        Intent serviceOne = new Intent();
        serviceOne.setClass(k.app(), LiveServiceOne.class);
        k.app().startService(serviceOne);
    }
}
