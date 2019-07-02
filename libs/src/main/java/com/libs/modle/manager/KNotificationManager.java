package com.libs.modle.manager;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import com.libs.R;
import com.libs.k;
import com.libs.utils.bengUtil.PendingIntentUtil;
import com.libs.utils.image.BitmapUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：通知管理器
 */
public class KNotificationManager {
    /**
     * 获取通知管理器
     */
    public static NotificationManager getNotificationManager() {
        return (NotificationManager) k.app().getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 开启通知
     *
     * @param id      不同的通知id必须不同
     * @param builder 通知信息的构造者
     */
    public static NotificationManager actionNotification(int id, NotificationCompat.Builder builder) {
        return actionNotification(null, 0, id, builder);
    }

    /**
     * 开启通知
     *
     * @param id      不同的通知id必须不同
     * @param flag    //提醒效果常用 Flag
     *                //Notification.FLAG_INSISTENT; 让声音、振动无限循环，直到用户响应 （取消或者打开）
     *                //Notification.FLAG_SHOW_LIGHTS:三色灯提醒，在使用三色灯提醒时候必须加该标志符
     *                //Notification.FLAG_ONGOING_EVENT:发起正在运行事件（活动中）,不能被手动清除,但能通过 cancel() 方法清除,等价于 builder.setOngoing(true);
     *                //Notification.FLAG_ONLY_ALERT_ONCE:发起Notification后，铃声和震动均只执行一次(常用)
     *                // Notification.FLAG_AUTO_CANCEL:用户单击通知后自动消失,等价于 builder.setAutoCancel(true);
     *                //Notification.FLAG_NO_CLEAR:只有调用NotificationManager.cancel()时才会清除
     *                //Notification.FLAG_FOREGROUND_SERVICE:表示正在运行的服务
     * @param builder 通知信息的构造者
     */
    public static NotificationManager actionNotification(int id, int flag, NotificationCompat.Builder builder) {
      return   actionNotification(null, flag, id, builder);
    }

    /**
     * 开启通知
     *
     * @param manager2 通知管理器
     * @param flag    标识
     * @param id      id，不同的通知id必须不同
     * @param builder 通知信息的构造者
     */
    public static NotificationManager actionNotification(NotificationManager manager2, int flag, int id, NotificationCompat.Builder builder) {
        NotificationManager manager=manager2;

        if (manager != null) {
            manager.notify(id, builder.build());
        } else {
            Notification notify = builder.build();
            notify.flags |= flag;
            getNotificationManager().notify(id, notify);
        }
        return manager;
    }

    public static NotificationCompat.Builder getDemoBuilder(Activity activity, Class<?> cls) {
        return new NotificationCompat.Builder(k.app())
                //首次进入时显示效果
                .setTicker("有新通知了")
                //设置大图标
                .setLargeIcon(BitmapUtil.getBitmap(R.mipmap.aa))
                //设置显示在顶部状态栏中的小图标
                .setSmallIcon(R.mipmap.aa)
                //设置标题
                .setContentTitle("通知标题")
                //设置内容
                .setContentText("通知内容")
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                .setNumber(13)
                //设置默认响应效果：设置此属性后setSound()、 setVibrate()、setLights()会无效
                // DEFAULT_SOUND：系统默认铃声
                // DEFAULT_VIBRATE：系统默认震动（需震动权限<uses-permission android:name="android.permission.VIBRATE"/>）
                // DEFAULT_LIGHTS：系统默认闪光（需闪光灯权限<uses-permission android:name="android.permission.FLASHLIGHT"/>）
                // DEFAULT_ALL：铃声、闪光、震动均系统默认
                .setDefaults(Notification.DEFAULT_ALL)
//                .setSound(Uri.parse("android.resource://" + AppInfoUtil.getAppPackageName() + "/" + R.raw.tips))
                //自定义震动效果
                //.setVibrate(new long[]{0, 500, 1000, 1500})
                //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
                //.setLights(0xFF0000, 3000, 3000)

//                8.0 也就是O以上必须设置，待测
//                .setChannel("aaaa")


                //另一种设置铃声的方法
                //Notification notify = builder.build();
                //调用系统默认铃声
                //notify.defaults = Notification.DEFAULT_SOUND;
                //调用自己提供的铃声
                //notify.sound = Uri.parse("android.resource://com.littlejie.notification/"+R.raw.sound);
                //调用系统自带的铃声
                //notify.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2");
                //mManager.notify(2,notify);
                .setContentIntent(PendingIntentUtil.getPendingIntentForActivity(new Intent(activity, cls)))

                ;

    }

    public static void cancel(int id) {
        getNotificationManager().cancel(id);
    }

    public static void cancel(int id, int flag) {
        getNotificationManager().cancel(flag + "", id);
    }

    public static void cancelAll() {
        getNotificationManager().cancelAll();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) k.app().getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) k.app().getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = k.app().getApplicationInfo();
        String pkg = k.app().getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isNotificationEnabled2(){
        return NotificationManagerCompat.from(k.app()).areNotificationsEnabled();
    }
}
