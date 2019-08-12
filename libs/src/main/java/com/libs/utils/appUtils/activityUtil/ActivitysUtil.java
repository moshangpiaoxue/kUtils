package com.libs.utils.appUtils.activityUtil;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.libs.k;
import com.libs.modle.listener.KActivityLifecycleCallbacks;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Stack;


/**
 * description: activity控制工具类
 * autour: mo
 * date: 2017/9/15 0015 10:02
 */
public class ActivitysUtil {


    private static Stack<Activity> activityStack;
    private static WeakReference<Activity> sTopActivityWeakRef;

    public static Application.ActivityLifecycleCallbacks getCallBack() {
        Application.ActivityLifecycleCallbacks mCallbacks = new KActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                addActivity(activity);
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                setTopActivityWeakRef(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityStack.remove(activity);
            }
        };
        return mCallbacks;
    }

    /**
     * 设置当前界面的弱引用
     *
     * @param activity
     */
    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    /**
     * 拿到当前界面的弱引用
     *
     * @return
     */
    public static WeakReference<Activity> getTopActivity() {
        return sTopActivityWeakRef;
    }
    /**
     * 获取顶层 Activity
     *
     * @return
     */
    public static String getTopActivity2() {
        ActivityManager manager = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        return runningTaskInfos == null ? "" : runningTaskInfos.get(0).topActivity.getClassName();
    }
    /**
     * 添加Activity 到栈
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前的Activity（堆栈中最后一个压入的)
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        activity.finish();
    }


    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名以外的其他所有Activity
     *
     * @param cls
     */
    public static void finishOtherActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有的Activity、
     */
    public static void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     *    
     *  退出应用程序   
     *      
     */
    public static void AppExit() {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(k.app().getPackageName());
            System.exit(0);
        } catch (Exception e) {

        }
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 判断是否存在指定Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isExistActivity(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }


}
