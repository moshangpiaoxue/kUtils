package com.libs.utils.appUtils;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.libs.k;

import java.io.File;


/**
 * @ author：mo
 * @ data：2019/2/13:11:55
 * @ 功能：Context 相关
 */
public class ContextUtil {

    /**
     * 获取当前 APP 的包名
     *
     * @return 当前 APP 的包名
     */
    public static String getPackageName() {
        return k.app().getPackageName();
    }

    /**
     * 获取 Resources, 在不方便获取 Context 的地方直接获取 Resources, 简化代码
     */
    public static Resources getResources() {
        return k.app().getResources();
    }

    /**
     * 获取 PackageManager, 在不方便获取 Context 的地方直接获取 PackageManager, 简化代码
     */
    public static PackageManager getPackageManager() {
        return k.app().getPackageManager();
    }

    /**
     * 启动 Activity
     */
    public static void startActivity(Intent intent) {
        k.app().startActivity(intent);
    }

    /**
     * 获取系统服务
     *
     * @param name 系统服务名
     */
    public static Object getSystemService(String name) {
        return k.app().getSystemService(name);
    }

    /**
     * 启动服务
     */
    public static void startService(Intent intent) {
        k.app().startService(intent);
    }

    /**
     * 停止服务
     */
    public static boolean stopService(Intent intent) {
        return k.app().stopService(intent);
    }

    /**
     * 获取应用缓存文件夹
     */
    public static File getCacheDir() {
        return k.app().getCacheDir();
    }

    /**
     * 获取应用数据文件夹
     */
    public static File getFilesDir() {
        return k.app().getFilesDir();
    }

    /**
     * 获取应用外部缓存文件夹
     */
    public static File getExternalCacheDir() {
        return k.app().getExternalCacheDir();
    }

    public static ContentResolver getContentResolver() {
        return k.app().getContentResolver();
    }
}
