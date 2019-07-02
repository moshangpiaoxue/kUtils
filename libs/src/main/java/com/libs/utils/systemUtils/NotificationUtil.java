package com.libs.utils.systemUtils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.libs.R;
import com.libs.k;
import com.libs.modle.broadcastreceivers.NotificationBroadcastReceiver;
import com.libs.utils.dataUtil.StringUtil;


/**
 * @ author：mo
 * @ data：2019/4/8:14:36
 * @ 功能：通知工具类
 * NotificationUtil.builder()
 * .setNotifyId(1)
 * .setChannelId((cha++) + "")
 * .setnotificFlag(Notification.FLAG_AUTO_CANCEL)
 * .setTicke("1111")
 * .setTitle("标题")
 * .setContent("内容")
 * .setSmallIcon(R.mipmap.aa)
 * .setReceiver(NotificationBroadcastReceiver2.class)
 * .actionNotification();
 */
public class NotificationUtil {
    /**
     * 通道id（8.0必须）
     */
    private static String mChannelId = "";
    /**
     * 通知id，一个通知对应一个id，不能重复
     */
    private static int mNotifyId = 0;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        /**
         * 通知 提醒效果 Flag
         * 常用 Flag
         * //Notification.FLAG_INSISTENT; 让声音、振动无限循环，直到用户响应 （取消或者打开）
         * //Notification.FLAG_SHOW_LIGHTS:三色灯提醒，在使用三色灯提醒时候必须加该标志符
         * //Notification.FLAG_ONGOING_EVENT:发起正在运行事件（活动中）,不能被手动清除,但能通过 cancel() 方法清除,等价于 builder.setOngoing(true);
         * //Notification.FLAG_ONLY_ALERT_ONCE:发起Notification后，铃声和震动均只执行一次(常用)
         * // Notification.FLAG_AUTO_CANCEL:用户单击通知后自动消失,等价于 builder.setAutoCancel(true);
         * //Notification.FLAG_NO_CLEAR:只有调用NotificationManager.cancel()时才会清除
         * //Notification.FLAG_FOREGROUND_SERVICE:表示正在运行的服务
         */
        private int notificFlag = Notification.FLAG_AUTO_CANCEL;
        /**
         * 意图生效后的效果
         */
        private int padingIntentFlag = PendingIntent.FLAG_CANCEL_CURRENT;
        /**
         * 首次进入时显示效果
         */
        private String ticke = "";
        /**
         * 标题
         */
        private String title = "";
        /**
         * 内容
         */
        private String content;
        /**
         * 显示在顶部状态栏中的小图标
         */
        private int smallIcon;
        /**
         * 点击后的跳转意图
         */
        private Intent clickIntent;
        /**
         * 通知栏消失后的意图
         */
        private Intent dismissIntent;
        /**
         * 接收推送的广播
         */
        private Class receiver = NotificationBroadcastReceiver.class;


        public Builder() {
        }


        public Builder setNotifyId(int notifyId) {
            mNotifyId = (notifyId <= mNotifyId ? ++mNotifyId : notifyId);
            return this;
        }

        public Builder setChannelId(String channelId) {
            mChannelId = mChannelId.equals(channelId) ? mNotifyId + "" : channelId;
            return this;
        }

        public Builder setnotificFlag(int notificFlag) {
            this.notificFlag = notificFlag;
            return this;
        }

        public Builder setPadingIntentFlag(int padingIntentFlag) {
            this.padingIntentFlag = padingIntentFlag;
            return this;
        }

        public Builder setTicke(String ticke) {
            this.ticke = ticke;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setSmallIcon(int smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }

        public Builder setClickIntent(Intent clickIntent) {
            this.clickIntent = clickIntent;
            return this;
        }

        public Builder setDismissIntent(Intent dismissIntent) {
            this.dismissIntent = dismissIntent;
            return this;
        }

        public Builder setReceiver(Class receiver) {
            this.receiver = receiver;
            return this;
        }

        public void actionNotification() {
            //通过通道id获取通知构造器
            NotificationCompat.Builder builder = new NotificationCompat.Builder(k.app(), mChannelId);
            if (!StringUtil.isEmpty(ticke)) {
                builder.setTicker(ticke);
            }
            builder.setSmallIcon(smallIcon == 0 ? R.mipmap.aa : smallIcon);
            //设置标题
            builder.setContentTitle(title);
            //设置内容
            builder.setContentText(content);
            if (clickIntent != null) {
                builder.setContentIntent(PendingIntent.getActivity(k.app(), 0, clickIntent, padingIntentFlag));
            }
            builder.setContentIntent(PendingIntent.getBroadcast(k.app(), 0, getDefClickIntent(), padingIntentFlag));

            //根据版本选择开启方式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getNotificationManager().notify(mNotifyId, getNotification(builder, notificFlag));
            } else {
                getNotificationManagerCompat().notify(mNotifyId, getNotification(builder, notificFlag));
            }
        }

        private Intent getDefClickIntent() {
            /*创建通知栏的点击事件*/
            Intent click = new Intent(k.app(), receiver);
            click.setAction("notification_clicked");
            click.putExtra("type", 1);
            return click;
        }
    }

//
//    /**
//     * 开启通知栏
//     *
//     * @param notifyId  不同的通知id必须不同
//     * @param channelId 通道id（8.0必须）
//     * @param flag      提醒效果常用 Flag
//     * @param ticke     首次进入时显示效果
//     * @param title     标题
//     * @param content   内容
//     * @param smallIcon 显示在顶部状态栏中的小图标
//     * @param intent    跳转意图
//     */
//    public static void actionNotification(int notifyId, String channelId, int flag, String ticke, String title, String content, int smallIcon, Intent intent
//            , Class receiver) {
//        /*创建通知栏的点击事件*/
//        Intent click = new Intent(k.app(), receiver);
//        click.setAction("notification_clicked");
//        click.putExtra("type", 1);
//        PendingIntent intentClick = PendingIntent.getBroadcast(k.app(), 0, click, PendingIntent.FLAG_CANCEL_CURRENT);
//        Intent dele = new Intent(k.app(), receiver);
//        dele.setAction("notification_cancelled");
//        dele.putExtra("type", 1);
//        PendingIntent intentDel = PendingIntent.getBroadcast(k.app(), 0, dele, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        //通过通道id获取通知构造器
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(k.app(), channelId)
//                //首次进入时显示效果
//                .setTicker(ticke)
//                //设置图标
//                .setSmallIcon(smallIcon == 0 ? R.mipmap.aa : smallIcon)
//                //设置标题
//                .setContentTitle(title)
//                //设置内容
//                .setContentText(content)
//                .setAutoCancel(true)
//                .setContentIntent(intentClick)
//                .setDeleteIntent(intentDel);
//
////        //设置跳转
////        if (intent != null) {
////            builder.setContentIntent(PendingIntent.getActivity(k.app(), 0, click, PendingIntent.FLAG_UPDATE_CURRENT));
////        }
////        builder.setDeleteIntent(PendingIntent.getActivity(k.app(), 0, dele, PendingIntent.FLAG_UPDATE_CURRENT));
//        //根据版本选择开启方式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getNotificationManager().notify(notifyId, getNotification(builder, flag));
//        } else {
//            getNotificationManagerCompat().notify(notifyId, getNotification(builder, flag));
//        }
//    }

    /**
     * 根据id 关闭通知
     *
     * @param notifyId
     */
    public static void cancel(int notifyId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().cancel(notifyId);
        } else {
            getNotificationManagerCompat().cancel(notifyId);
        }
    }

    /**
     * 关闭全部通知
     */
    public static void cancelAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().cancelAll();
        } else {
            getNotificationManagerCompat().cancelAll();
        }
    }

    /**
     * 获取通知管理器(8.0以上)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static NotificationManager getNotificationManager() {
        NotificationManager notificationManager = (NotificationManager) k.app().getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(mChannelId, "channel_name", importance);
            notificationManager.createNotificationChannel(channel);
        }
        return notificationManager;
    }

    /**
     * 获取通知管理器(8.0以下)
     */
    private static NotificationManagerCompat getNotificationManagerCompat() {
        return NotificationManagerCompat.from(k.app());
    }

    /**
     * 获取通知实例
     *
     * @param channelId 通道id
     * @return
     */
    /**
     * 获取通知实例
     *
     * @param builder 通知构造器
     * @param flag    //提醒效果常用 Flag
     *                *                //Notification.FLAG_INSISTENT; 让声音、振动无限循环，直到用户响应 （取消或者打开）
     *                *                //Notification.FLAG_SHOW_LIGHTS:三色灯提醒，在使用三色灯提醒时候必须加该标志符
     *                *                //Notification.FLAG_ONGOING_EVENT:发起正在运行事件（活动中）,不能被手动清除,但能通过 cancel() 方法清除,等价于 builder.setOngoing(true);
     *                *                //Notification.FLAG_ONLY_ALERT_ONCE:发起Notification后，铃声和震动均只执行一次(常用)
     *                *                // Notification.FLAG_AUTO_CANCEL:用户单击通知后自动消失,等价于 builder.setAutoCancel(true);
     *                *                //Notification.FLAG_NO_CLEAR:只有调用NotificationManager.cancel()时才会清除
     *                *                //Notification.FLAG_FOREGROUND_SERVICE:表示正在运行的服务
     * @return
     */
    private static Notification getNotification(NotificationCompat.Builder builder, int flag) {
        Notification notify = builder.build();
        if (flag != 0) {
            notify.flags |= flag;
        }
        return notify;
    }
}
