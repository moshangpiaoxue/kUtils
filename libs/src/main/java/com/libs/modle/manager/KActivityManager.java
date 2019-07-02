package com.libs.modle.manager;

import android.app.ActivityManager;
import android.content.Context;

import com.libs.k;

import java.util.List;


/**
 * @ author：mo
 * @ data：2019/2/13:16:19
 * @ 功能：
 */
public enum KActivityManager {

    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 获取活动管理器
     */
    public ActivityManager getActivityManager() {
        return (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取运行中的服务
     */
    public List<ActivityManager.RunningServiceInfo> getRunningServiceInfo() {
        return getActivityManager().getRunningServices(1000);
    }

}
