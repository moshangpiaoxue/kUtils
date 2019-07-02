package com.libs.utils.systemUtils.fingerUtil;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;

/**
 * @ author：mo
 * @ data：2019/5/16：9:34
 * @ 功能：指纹识别实现类，版本不同，使用的方法不同，，不用的方法都继承这个接口，在管理类里就可以直接掉
 */
interface FingerImpl {
    /**
     *
     * @param cancel 取消控制器
     * @param callback 结果回调
     */
    void authenticate(@NonNull CancellationSignal cancel, @NonNull OnBiometricIdentifyCallback callback);

}
