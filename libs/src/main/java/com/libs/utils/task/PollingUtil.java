package com.libs.utils.task;

import android.app.Activity;
import android.os.Handler;

import com.libs.utils.logUtils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ author：mo
 * @ data：2019/8/5:15:48
 * @ 功能：轮询工具类（Handler实现）
 */
public class PollingUtil {
    private Handler mHanlder;
    private Map<Runnable, Runnable> mTaskMap = new HashMap<Runnable, Runnable>();
    private Runnable runnable;

    public PollingUtil() {
        mHanlder = HandlerUtil.getMainHandler();
    }

    /**
     * 开启定时任务
     *
     * @param runnable 任务
     * @param interval 时间间隔
     */
    public void startPolling(Runnable runnable, long interval) {
        startPolling(runnable, interval, false);
    }

    /**
     * 开启定时任务
     *
     * @param runnable       任务
     * @param interval       时间间隔
     * @param runImmediately 是否先立即执行一次
     */
    public void startPolling(final Runnable runnable, final long interval,
                             boolean runImmediately) {

        if (runImmediately) {
            runnable.run();
        }
        Runnable task = mTaskMap.get(runnable);
        if (task == null) {
            task = new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                    post(runnable, interval);
                }
            };
            mTaskMap.put(runnable, task);
        }
        post(runnable, interval);
    }

    /**
     * 结束某个定时任务
     *
     * @param runnable 任务
     */
    public void endPolling(Runnable runnable) {
        if (mTaskMap.containsKey(runnable)) {
            mHanlder.removeCallbacks(mTaskMap.get(runnable));
        }
    }
    /**
     *
     * 暂时这么写，不知道多任务会是什么情况
     */
    public void endPolling() {
       if (runnable!=null){
           mHanlder.removeCallbacks(mTaskMap.get(runnable));
       }
    }

    /**
     * 添加任务
     */
    private void post(Runnable runnable, long interval) {
        LogUtil.i("AAAAAAAA");
        this.runnable=runnable;
        Runnable task = mTaskMap.get(runnable);
        mHanlder.removeCallbacks(task);
        mHanlder.postDelayed(task, interval);
    }

}
