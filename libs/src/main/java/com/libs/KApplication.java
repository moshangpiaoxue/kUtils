package com.libs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * author：mo
 * data：2017/11/4 0004
 * 功能：Application 基类
 */

public class KApplication extends MultiDexApplication {
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * 主线程
     */
    private static Thread mMainThread;
    /**
     * 主线程id
     */
    private static long mMainThreadId;
    /**
     * 循环队列
     */
    private static Looper mMainLooper;
    /**
     * 主线程Handler
     */
    private static Handler mHandler;


    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        KApplication.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        KApplication.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        KApplication.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        KApplication.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        KApplication.mHandler = mHandler;
    }

    /**
     * 计数器
     */
    private int appCount = 0;
    private Boolean isActive = false;

    @Override
    public void onCreate() {
        super.onCreate();
        k.Ext.init(this);
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

    }



    /**
     * 监听程序前后台切换
     */
    public void actionListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
                if (isActive) {
                    //应用从后台回到前台 需要做的操作
                    onBackApp(activity);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
                if (appCount == 0) {
                    //应用进入后台 需要做的操作
                    onLeaveApp(activity);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 从后台返回app
     *
     * @param activity
     */
    protected void onBackApp(Activity activity) {
        isActive = false;
    }

    /**
     * 程序进入后台或者退出应用
     *
     * @param activity
     */
    protected void onLeaveApp(Activity activity) {
        isActive = true;
    }

    /**
     * 解决65536
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

