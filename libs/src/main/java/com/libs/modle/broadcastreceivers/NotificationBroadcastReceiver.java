package com.libs.modle.broadcastreceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * @ author：mo
 * @ data：2019/4/8:17:16
 * @ 功能：通知广播
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {
    public static final String TYPE = "type"; //这个type是为了Notification更新信息的

    public NotificationBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra("type", -1);
        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            onClick();
            //处理点击事件
//            Intent intent1 = new Intent(context, ListActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);
        }

        if (action.equals("notification_cancelled")) {
            //处理滑动事件
//            Intent intent2 = new Intent(context, ListActivity.class);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.stopService(intent2);
            onCanaell();
        }

    }

    protected void onCanaell() {

    }

    protected void onClick() {

    }
}
