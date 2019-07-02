package com.kutils;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.libs.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/2/15:17:00
 * @ 功能：
 */
public class TimeService extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        LogUtil.i("后台进程被创建。 ");

//服务启动广播接收器，使得广播接收器能够在程序退出后在后天继续运行。接收系统时间变更广播事件
        TimeChangeReceiver receiver = new TimeChangeReceiver();
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.i( "后台进程。。 。");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {

        LogUtil.i("后台进程被销毁了。。。 ");

        super.onDestroy();
    }

}