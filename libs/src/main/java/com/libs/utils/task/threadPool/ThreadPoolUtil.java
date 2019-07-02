package com.libs.utils.task.threadPool;

import android.os.Handler;


/**
 * description: 线程池工具类
 * autour: mo
 * date: 2017/9/8 0008 20:45
 */

public class ThreadPoolUtil {
    //在非UI线程中执行
    public static void runTaskInThread(Runnable task) {
        ThreadPoolFactory.INSTANCE.getThreadPool().executeTask(task);
    }

    //在UI线程中执行
    private static Handler handler = new Handler();

    public static void runTaskInUIThread(Runnable task) {
        handler.post(task);
    }
}
