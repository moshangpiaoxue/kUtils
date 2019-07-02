package com.libs.utils.task;

import android.os.Handler;

import com.libs.k;


/**
 * @ author：mo
 * @ data：2017/1/29：13:43
 * @ 功能：异步任务的管理类的实现类
 */
public final class KTaskControllerImpl implements KTaskController {

    private KTaskControllerImpl() {
    }

    private static volatile KTaskController instance;

    public static void registerInstance() {
        if (instance == null) {
            synchronized (KTaskController.class) {
                if (instance == null) {
                    instance = new KTaskControllerImpl();
                }
            }
        }
        k.Ext.setKTaskController(instance);
    }


    @Override
    public void autoPost(Runnable runnable) {
    }

    /**
     * run in UI thread
     *
     * @param runnable
     */
    @Override
    public void post(Runnable runnable) {
    }

    /**
     * 在UI线程执行runnable
     *
     * @param runnable
     * @param delayMillis 延迟时间(单位毫秒)
     */
    @Override
    public void postDelayed(Runnable runnable, long delayMillis) {
        if (runnable == null) {
            return;
        }
        new Handler().postDelayed(runnable, delayMillis);
    }

    /**
     * run in background thread
     *
     * @param runnable
     */
    @Override
    public void run(Runnable runnable) {
    }

    /**
     * 移除post或postDelayed提交的, 未执行的runnable
     *
     * @param runnable
     */
    @Override
    public void removeCallbacks(Runnable runnable) {
    }
}
