package com.libs.utils.task;

import android.os.Process;

/**
 * @ author：mo
 * @ data：2019/2/13:18:32
 * @ 功能：线程相关
 */
public class ThreadUtil {
    private static int sMainTid;

    private ThreadUtil() {
    }

    static void init() {
        sMainTid = Process.myTid();
    }

    /**
     * 获取当前线程名
     *
     * @return 当前线程名
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 判断是否为主线程
     *
     * @param tid 当前线程的 thread id
     * @return true 是; false 不是
     */
    public static boolean isMainThread(int tid) {
        return sMainTid == tid;
    }
}
