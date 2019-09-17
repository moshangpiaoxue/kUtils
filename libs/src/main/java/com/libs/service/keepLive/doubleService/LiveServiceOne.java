package com.libs.service.keepLive.doubleService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.ServiceUtil;


/**
 * @ author：mo
 * @ data：2019/2/15:18:23
 * @ 功能：双服务+广播保活 之 服务1
 */
public class LiveServiceOne extends Service {

    public LiveServiceOne() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("LiveServiceOne-->>onStartCommand");
        startServiceTwo();
        //如果这个Service被系统Kill了或者app被Kill了，Service还能自动重启。
        return START_REDELIVER_INTENT;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i( "LiveServiceOne-->>服务创建了");
        startServiceTwo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("LiveServiceOne-->>服务销毁了");
        startServiceTwo();
    }

    private void startServiceTwo() {
        //myservice2是MyServiceTwo的名字，我们会在manifest中定义，这里必须和manifest中定义的一样
        boolean b = ServiceUtil.isRuning(LiveServiceTwo.class);
        if(!b) {
            Intent service = new Intent(LiveServiceOne.this, LiveServiceTwo.class);
            startService(service);
            LogUtil.i( "LiveServiceOne-->> Start ServiceTwo");
        }
    }
}