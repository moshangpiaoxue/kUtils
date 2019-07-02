package com.libs.modle.service.keepLive;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.libs.k;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.tipsUtil.ToastUtil;

import java.util.List;


/**
 * @ author：mo
 * @ data：2019/1/24
 * @ 功能：
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class KNotificationListenerService extends NotificationListenerService {

    /**
     * 当连接成功时调用，一般在开启监听后会回调一次该方法
     */
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        LogUtil.i("开启监听");
    }

    /**
     * 当收到一条消息时回调，sbn里面带有这条消息的具体信息
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        LogUtil.i("收到一条消息" + isUIProcess());

    }


    /**
     * 当移除一条消息的时候回调，sbn是被移除的消息
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        LogUtil.i("删除了一条消息" + isUIProcess());
    }

    //判断是否在主进程,这个方法判断进程名或者pid都可以,如果进程名一样那pid肯定也一样
//true:当前进程是主进程 false:当前进程不是主进程
    public boolean isUIProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 重启服务
     */
    public static void toggleNotificationListenerService() {
        PackageManager pm = k.app().getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(k.app(), KNotificationListenerService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(k.app(), KNotificationListenerService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 判断是否拥有通知权限
     */
    public static boolean isNotificationListenersEnabled() {
        String pkgName = k.app().getPackageName();
        final String flat = Settings.Secure.getString(k.app().getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean gotoNotificationAccessSetting() {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            k.app().startActivity(intent);
            return true;

        } catch (ActivityNotFoundException e) {//普通情况下找不到的时候需要再特殊处理找一次
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                k.app().startActivity(intent);
                return true;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            ToastUtil.showToast("对不起，您的手机暂不支持");
            e.printStackTrace();
            return false;
        }
    }
}
