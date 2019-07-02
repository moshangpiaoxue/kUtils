package com.libs.utils.bengUtil;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SmsManager;

import com.libs.k;
import com.libs.utils.appUtils.PermissionUtil;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.logUtils.LogUtil;

import java.util.List;
import static android.app.Notification.EXTRA_CHANNEL_ID;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.provider.Settings.EXTRA_APP_PACKAGE;


/**
 * @ author：mo
 * @ data：2018/12/11
 * @ 功能：
 */
public class NextOtherActivityUtil {
    /**
     * 打开 App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        k.app().startActivity(IntentUtil.getLaunchAppIntent(packageName));
    }

    /**
     * 打开 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtil.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void toDial(final String phoneNumber) {
        k.app().startActivity(IntentUtil.getDialIntent(phoneNumber));
    }

    /**
     * 拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static void toCall(final String phoneNumber) {
        if (PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.CALL_PHONE)) {
            LogUtil.i("没权限");
            return;
        }
        k.app().startActivity(IntentUtil.getCallIntent(phoneNumber));
    }

    /**
     * 跳至发送短信界面
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void toSendSms(final String phoneNumber, final String content) {
        k.app().startActivity(IntentUtil.getSendSmsIntent(phoneNumber, content));
    }

    /**
     * 发送短信
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SEND_SMS" />}</p>
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void toSendSms2(final String phoneNumber, final String content) {
        if (StringUtil.isEmpty(content)) {
            return;
        }
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.SEND_SMS)) {
            LogUtil.i("没权限");
            return;
        }
        PendingIntent sentIntent = PendingIntent.getBroadcast(k.app(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

    /**
     * 跳app系统设置
     */
    public static void toSettingApp() {
        toSettingApp(k.app().getPackageName());
    }

    /**
     * 跳app系统设置
     *
     * @param packageName 包名
     */
    public static void toSettingApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        k.app().startActivity(IntentUtil.getSettingAppIntent(packageName));
    }

    /**
     * 跳网络设置
     */
    public static void toSettingNet() {
        k.app().startActivity(IntentUtil.getSettingNetIntent());
    }

    /**
     * 调起浏览器
     *
     * @param url 浏览器地址
     */
    public static void toBrowser(String url) {
        String temp;
        if (!url.contains("http")) {
            temp = "http://" + url;
        } else {
            temp = url;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(temp);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            k.app().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("异常了，");
        }


    }

    /**
     * 设置通知栏 权限
     */
    public static void toSettingNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", k.app().getPackageName());
            intent.putExtra("app_uid", k.app().getApplicationInfo().uid);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            k.app().startActivity(intent);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + k.app().getPackageName()));
            k.app().startActivity(intent);
        } else {
            Intent localIntent = new Intent();
            localIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", k.app().getPackageName(), null));
            k.app().startActivity(localIntent);

        }
    }

    /**
     * 这个方法会把吐司也关了
     */
    public static void toSettingNotification2(Activity activity) {
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(EXTRA_APP_PACKAGE, activity.getPackageName());
            intent.putExtra(EXTRA_CHANNEL_ID, activity.getApplicationInfo().uid);

            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", activity.getPackageName());
            intent.putExtra("app_uid", activity.getApplicationInfo().uid);

            // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
            //  if ("MI 6".equals(Build.MODEL)) {
            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
            //      intent.setData(uri);
            //      // intent.setAction("com.android.settings/.SubSettings");
            //  }
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();

            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        }
    }
}
