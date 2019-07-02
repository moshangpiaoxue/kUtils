package com.libs.utils.systemUtils.fingerUtil;

/**
 * @ author：mo
 * @ data：2019/5/16:9:52
 * @ 功能：指纹识别结果回调
 */
public interface OnBiometricIdentifyCallback {

    /**
     * 成功
     */
    void onSucceeded();

    /**
     * 验证失败
     */
    void onFailed();

    /**
     * 报错 code==7超过次数（在一定时间内，对指纹验证有次数限制，）
     */
    void onError(int code, String reason);

    /**
     * 取消
     */
    void onCancel();

}
