package com.libs.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.libs.utils.bengUtil.NextActivityUtil;
import com.libs.utils.logUtils.LogUtil;

import java.util.List;



/**
 * @ User：mo
 * <>
 * @ 功能：Activity基类。主要是一些复写方法
 * <>
 * @ 入口：
 * <>
 * @ Time：2018/8/13 0013 14:33
 * <p>
 * 一、横竖屏切换
 * 1、不设置Activity的android: configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次。
 * 2、设置Activity的android: configChanges=“orientation”时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次。
 * 3、设置Activity的android: configChanges=“orientation|keyboardHidden”时，切屏不会重新调用各个生命周期，只会执行onConfiguration方法
 * <p>
 * 二、启动模式 launchMode
 * 1、standard：默认，每次都新建
 * 2、singleTop：单一置顶，如果用过，并且处于栈顶，就用这个不新建，否则新建，比如：接收通知启动的界面
 * 3、singleTask：单一任务，如果用过，没有处于栈顶，把他上面的顶没，使其处于栈顶，比如：程序入口
 * 4、singleInstance：如果有两个以上的地方都要用这个activity，从第二次调用开始，会把这个activity单独放在一个栈里，比如：闹铃界面
 */

public class BActivity extends AppCompatActivity {
    /**
     * app是否处于前台
     */
    public static boolean isActive;
    /**
     * 上下文
     */
    protected Context mContext;
    protected Activity mActivity;
    /**
     * 携带数据
     */
    protected Intent mIntent;

    /**
     * 在当前界面写好跳入方法，可以在多人开发的时候，减少工作量
     */
    public static void nextActivity(Activity fromAct, Intent mIntent, final Boolean isFinish) {
        NextActivityUtil.toNextActivity(fromAct, mIntent, isFinish);
    }


    /**
     * 创建，不可见
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mIntent = getIntent();
    }

    /**
     * 出现场景：
     * <p>
     * 1、当用户按下HOME键时。
     * 2、长按HOME键，选择运行其他的程序时。
     * 3、按下电源按键（关闭屏幕显示）时。
     * 4、从activity A中启动一个新的activity时。
     * 5、屏幕方向切换时，例如从竖屏切换到横屏时。
     * 6、activity意外销毁
     * <p>
     * }
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("aa", "bbb");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.i(savedInstanceState.getString("aa"));
    }

    /**
     * 开始，可见
     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    /**
     * 恢复，可见
     */
    @Override
    protected void onResume() {
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
//            LogUtil.i("程序从后台唤醒");
            onBackApp();
        }
        super.onResume();
    }


    /**
     * 暂停，可见
     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

    /**
     * 横竖屏切换
     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }

    /**
     * 停止，不可见
     */
    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
//            LogUtil.i("程序进入后台");
            onLeaveApp();
        }
        super.onStop();
    }

    /**
     * 从后台返回app
     */
    protected void onBackApp() {
    }

    /**
     * 程序进入后台
     */
    protected void onLeaveApp() {

    }

    /**
     * 销毁，不可见
     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    /**
     * @param requestCode 请求码，用于与startActivityForResult中的requestCode中值进行比较判断，是以便确认返回的数据是从哪个Activity返回的
     * @param resultCode  结果码，是由子Activity通过其setResult()方法返回。适用于多个activity都返回数据时，来标识到底是哪一个activity返回的值
     * @param data        携带数据，一个Intent对象，带有返回的数据。可以通过data.getXxxExtra( );方法来获取指定数据类型的数据
     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    ///**
    // * 返回结果
    // * 由于使用的final修饰，所以不能复写，不能继承
    // * @param resultCode  结果码  对应onActivityResult里的resultCode
    // * @param data    携带数据
    // */
    //public final void setResult(int resultCode, Intent data) {
    //  synchronized (this) {
    //    mResultCode = resultCode;
    //    mResultData = data;
    //  }
    //}

    /**
     * 开始带数据给result方法
     *
     * @param intent      携带数据, 一个Intent对象
     * @param requestCode 请求码 必须大于等于 0，对应onActivityResult里的requestCode
     */
//    @Override
//    public void startActivityForResult(@RequiresPermission Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//    }

    /**
     * 1、当launchMode为singleTask的时候，如果这个界面处于栈顶也就是他正在显示的时候，使用intent跳转到这个界面想要刷新数据的时候，会没有反应，这个时候就要用到这个方法去接收数据
     * 2、当launchMode为singleTop 的时候，如果A在activity 栈顶，那么就会调用A 的onNewIntent 如果A不在栈顶，则不会调用。
     * 3、当launchMode为singleInstance  的时候，如果启动过activity A ，有activity A 的实例，那么就会调用onNewIntent
     * 4、当launchMode为默认，但是在代码里
     * Intent intent = new Intent(SecondActivity.this,MainActivity.class);
     * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     * intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     * 这个时候也会调用
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mIntent = intent;
    }

    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
