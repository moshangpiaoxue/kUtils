package com.libs.utils.logUtils;


import android.os.Build;
import android.util.Log;

import com.libs.k;
import com.libs.utils.appUtils.AppInfoUtil;


/**
 * @ author：mo
 * @ data：2017/11/29：14:29
 * @ 功能：log工具类
 */
public class LogUtil {


    private static final int V = 0x1;
    private static final int D = 0x2;
    private static final int I = 0x3;
    private static final int W = 0x4;
    private static final int E = 0x5;
    private static final int A = 0x6;
    private static final int MAX_LENGTH = 4000;
    private static String customTagPrefix = "mo";
    /**
     * 分隔器
     */
    private static final String SEPARATOR = "\t\t\t";
    /**
     * 骚话
     */
    public static String TAG = "==矮有钱垫脚，丑有钱整好，只有穷才是一辈子的烦恼！==";
    private static AppInfoUtil.AppInfo appInfo;
    /**
     * log的信息有4K的限制，这里判断，分割一下
     */
    private static void printDefault(int type, String msg) {
        if (!k.isDebug()) {
            return;
        }


        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, TAG + sub);
                index += MAX_LENGTH;
            }
            printSub(type, TAG + msg.substring(index, length));
        } else {
            printSub(type, TAG + msg);
        }
    }

    private static void printSub(int type, String sub) {
        Log.i(customTagPrefix, "╔═════════════════════════════飞哥告诉你，这是log！不谢 ^_^ \"══════════════════════════════════════════════════════════");
        Log.i(customTagPrefix,"                        ");
        switch (type) {
            case V:
                Log.v(customTagPrefix, sub);
                break;
            case D:
                Log.d(customTagPrefix, sub);
                break;
            case I:
                Log.i(customTagPrefix, sub);
                break;
            case W:
                Log.w(customTagPrefix, sub);
                break;
            case E:
                Log.e(customTagPrefix, sub);
                break;
            case A:
                Log.wtf(customTagPrefix, sub);
                break;
            default:
                break;
        }
        Log.i(customTagPrefix,"                                    ");
        Log.i(customTagPrefix, "╚═══════════════════════════════════════════════════════════════════════════════════════");

    }

    public static void v(String message) {
        printDefault(V, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    public static void d(String message) {
        printDefault(D, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    public static void i(String message) {
        appInfo = AppInfoUtil.getAppInfo();
        printDefault(I, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    public static void w(String message) {
        printDefault(W, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    public static void e(String message) {
        printDefault(E, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    public static void a(String message) {
        printDefault(A, getLogInfo(Thread.currentThread().getStackTrace()[3]) + message);
    }

    /**
     * 输出日志所包含的信息
     */
    private static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        logInfoStringBuilder.append("                 \n");
        logInfoStringBuilder.append("手机型号：" + Build.BRAND).append(SEPARATOR);
        logInfoStringBuilder.append("app 名称：" + appInfo.getName()).append(SEPARATOR);
        logInfoStringBuilder.append("版  本 码：" + AppInfoUtil.getVersionCode()).append(SEPARATOR);
        logInfoStringBuilder.append("版  本 号：" + appInfo.getVersionName()).append(SEPARATOR);
        logInfoStringBuilder.append("线  程 id：" + Thread.currentThread().getId()).append(SEPARATOR);
        logInfoStringBuilder.append("线  程 名：" + Thread.currentThread().getName()).append(SEPARATOR);
        logInfoStringBuilder.append("文  件 名：" + stackTraceElement.getFileName()).append(SEPARATOR);
        logInfoStringBuilder.append("类       名：" + stackTraceElement.getClassName()).append(SEPARATOR);
        logInfoStringBuilder.append("\n方  法 名：" + stackTraceElement.getMethodName()).append(SEPARATOR);
        logInfoStringBuilder.append("行       号：" + stackTraceElement.getLineNumber());
        logInfoStringBuilder.append("\n内       容：");
        return logInfoStringBuilder.toString();
    }


}
