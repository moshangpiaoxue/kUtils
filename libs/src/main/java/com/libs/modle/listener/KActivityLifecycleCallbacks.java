package com.libs.modle.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @ author：mo
 * @ data：2018/10/25
 * @ 功能：activity生命周期监听回调
 */
public class KActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    /**
     * 创建
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    /**
     * 开始
     */
    @Override
    public void onActivityStarted(Activity activity) {

    }

    /**
     * 恢复
     */
    @Override
    public void onActivityResumed(Activity activity) {

    }

    /**
     * 暂停
     */
    @Override
    public void onActivityPaused(Activity activity) {

    }

    /**
     * 停止
     */
    @Override
    public void onActivityStopped(Activity activity) {

    }

    /**
     * 保存状态
     */
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    /**
     * 销毁
     */
    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
