package com.libs.utils.systemUtils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.libs.k;
import com.libs.modle.manager.KActivityManager;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/2/13:16:25
 * @ 功能：
 */
public class ServiceUtil {
    /**
     * 判断服务是否正在运行
     *
     * @param service 服务类
     * @return 是否正在运行
     */
    public static boolean isRuning(Class<? extends Service> service) {
        // 获取运行中服务
        List<ActivityManager.RunningServiceInfo> services = KActivityManager.INSTANCE.getRunningServiceInfo();
        String serviceName = service.getName();
        for (ActivityManager.RunningServiceInfo info : services) {
            // 获取每一条运行中的服务的类名并判断
            String name = info.service.getClassName();
            if (TextUtils.equals(serviceName, name)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isRuning(String serviceName) {
        ActivityManager myManager = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
        //获取当前所有服务
        ArrayList<ActivityManager.RunningServiceInfo> runningService =
                (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 启动指定的服务
     *
     * @param clazz 服务类
     */
    public static void startService(Class<?> clazz) {
        k.app().startService(new Intent(k.app(), clazz));
    }

    /**
     * 停止指定的服务
     *
     * @param clazz 服务类
     */
    public static boolean stopService(Class<?> clazz) {
        return k.app().stopService(new Intent(k.app(), clazz));
    }
}
