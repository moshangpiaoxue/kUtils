package com.libs.modle.service.keepLive.doubleService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.ServiceUtil;


/**
 * @ author：mo
 * @ data：2019/2/15:18:23
 * @ 功能：双服务+广播保活 之 服务2
 */
public class LiveServiceTwo extends Service {

    public LiveServiceTwo() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("LiveServiceTwo-->>onStartCommand");
        startServiceOne();
        //如果这个Service被系统Kill了或者app被Kill了，Service还能自动重启。
        return START_REDELIVER_INTENT;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i( "LiveServiceTwo-->>服务创建了");
        startServiceOne();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("LiveServiceTwo-->>服务销毁了");
        startServiceOne();
    }

    private void startServiceOne() {
        //myservice2是MyServiceTwo的名字，我们会在manifest中定义，这里必须和manifest中定义的一样
        boolean b = ServiceUtil.isRuning(LiveServiceTwo.class);
        if(!b) {
            Intent service = new Intent(LiveServiceTwo.this, LiveServiceOne.class);
            startService(service);
            LogUtil.i( "LiveServiceTwo-->>Start ServiceOne");
        }
    }
}