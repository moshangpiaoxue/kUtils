package com.libs.utils.systemUtils.fingerUtil;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

import com.libs.view.dialog.IosAlertDialog;


/**
 * @ author：mo
 * @ data：2019/5/15：16:22
 * @ 功能：指纹识别管理器
 */
public class FingerDentifyManager {
    /**
     * 失败超过一定次数后，一定时间内不能验证，（没用，以后优化）
     */
    public static boolean isMuthError = false;
    private FingerImpl mImpl;
    private Activity mActivity;

    /**
     * 单例，一会换枚举
     */
    public static FingerDentifyManager from(Activity activity) {
        return new FingerDentifyManager(activity);
    }

    /**
     * 构造器
     */
    public FingerDentifyManager(Activity activity) {
        mActivity = activity;
        if (isAboveApi28()) {
            mImpl = new FingerPromptApi28(activity);
        } else if (isAboveApi23()) {
            mImpl = new FingerPromptApi23(activity);
        }
    }

    /**
     * 判断版本8.0
     */
    private boolean isAboveApi28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断版本6.0
     */
    private boolean isAboveApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 开始验证指纹
     */
    public void authenticate(@NonNull OnBiometricIdentifyCallback callback) {
        mImpl.authenticate(new CancellationSignal(), callback);
    }

    /**
     * 开始验证指纹
     */
    public void authenticate(@NonNull CancellationSignal cancel,
                             @NonNull OnBiometricIdentifyCallback callback) {
        mImpl.authenticate(cancel, callback);
    }


    /**
     * 判断用户是否设置了指纹
     */
    public boolean hasEnrolledFingerprints() {
        if (isAboveApi28()) {
            return ((FingerPromptApi28) mImpl).hasEnrolledFingerprints();
        } else if (isAboveApi23()) {
            return ((FingerPromptApi23) mImpl).hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    /**
     * 当前设备是否支持指纹
     */
    public boolean isHardwareDetected() {
        if (isAboveApi28()) {
            return ((FingerPromptApi28) mImpl).isHardwareDetected();
        } else if (isAboveApi23()) {
            return ((FingerPromptApi23) mImpl).isHardwareDetected();
        } else {
            return false;
        }
    }

    /**
     * 有没有锁屏密码
     */
    public boolean isKeyguardSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }
        return false;
    }

    /**
     * 是否可以开启指纹验证 （版本大于6.0+设备支持指纹+用户设置过指纹+用户设置了锁屏密码）
     */
    public boolean isBiometricPromptEnable(boolean isShow) {
        if (isShow) {
            if (!isAboveApi23()) {
                new IosAlertDialog(mActivity).builder().setMsg("您的系统版本过低不支持指纹登录，请取消").setCancelable(false).setNegativeButton("取消", null).show();
                return false;
            } else if (!isHardwareDetected()) {
                new IosAlertDialog(mActivity).builder().setMsg("您的设备不支持指纹登录，请取消").setCancelable(false).setNegativeButton("取消", null).show();
                return false;
            } else if (!hasEnrolledFingerprints()) {
                new IosAlertDialog(mActivity).builder().setMsg("您还没有录入指纹, 请在手机设置界面录入至少一个指纹").setCancelable(false).setNegativeButton("取消", null).show();
                return false;
            } else if (!hasEnrolledFingerprints()) {
                new IosAlertDialog(mActivity).builder().setMsg("您还没有设置锁屏密码, 请在手机设置界面设置").setCancelable(false).setNegativeButton("取消", null).show();
                return false;
            } else if (isMuthError) {
                new IosAlertDialog(mActivity).builder().setMsg("您的指纹验证多次错误，请稍后再试").setCancelable(false).setNegativeButton("取消", null).show();
                return false;
            } else {
                return true;
            }
        } else {
            return isAboveApi23()
                    && isHardwareDetected()
                    && hasEnrolledFingerprints()
                    && isKeyguardSecure()
                    && !isMuthError;
        }

    }

}
