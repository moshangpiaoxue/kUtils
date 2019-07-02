package com.libs.utils.systemUtils.fingerUtil;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.libs.ui.fragment.KDialogFragment;


/**
 * @ author：mo
 * @ data：2019/5/16：9:48
 * @ 功能：6.0以上指纹识别方法
 */
@RequiresApi(Build.VERSION_CODES.M)
public class FingerPromptApi23 implements FingerImpl {

    private static final String TAG = "FingerPromptApi23";
    private Activity mActivity;
    private FingerDentifyDialog mDialog;
    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private OnBiometricIdentifyCallback mManagerIdentifyCallback;
    private FingerprintManager.AuthenticationCallback mFmAuthCallback
            = new FingerprintManageCallbackImpl();

    public FingerPromptApi23(Activity activity) {
        mActivity = activity;

        mFingerprintManager = getFingerprintManager(activity);
    }

    @Override
    public void authenticate(@Nullable CancellationSignal cancel,
                             @NonNull OnBiometricIdentifyCallback callback) {
        //指纹识别的回调
        mManagerIdentifyCallback = callback;

        /**
         * 我实现了一个自定义dialog，
         * FingerDentifyDialog.OnBiometricPromptDialogActionCallback是自定义dialog的回调
         */
        mDialog = FingerDentifyDialog.newInstance();
        mDialog.setDialogFragmentCallBack(new KDialogFragment.DialogFragmentCallBack() {
            @Override
            public void onSure(KDialogFragment dialog) {

            }

            @Override
            public void onCancel(KDialogFragment dialog) {
                //点击cancel键
                if (mManagerIdentifyCallback != null) {
                    mManagerIdentifyCallback.onCancel();
                }
            }

            @Override
            public void onDialogDismiss() {
                //当dialog消失的时候，包括点击userPassword、点击cancel、和识别成功之后
                if (mCancellationSignal != null && !mCancellationSignal.isCanceled()) {
                    mCancellationSignal.cancel();
                }
            }
        });
        mDialog.show(mActivity.getFragmentManager(), "FingerPromptApi23");

        mCancellationSignal = cancel;
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }
        });

        try {
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            getFingerprintManager(mActivity).authenticate(
                    cryptoObjectHelper.buildCryptoObject(), mCancellationSignal,
                    0, mFmAuthCallback, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FingerprintManageCallbackImpl extends FingerprintManager.AuthenticationCallback {

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.d(TAG, "onAuthenticationError() called with: errorCode = [" + errorCode + "], errString = [" + errString + "]");
            mDialog.setState(FingerDentifyDialog.STATE_ERROR,errString.toString());
            mManagerIdentifyCallback.onError(errorCode, errString.toString());
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.d(TAG, "onAuthenticationFailed() called");
            mDialog.setState(FingerDentifyDialog.STATE_FAILED,"");
            mManagerIdentifyCallback.onFailed();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Log.d(TAG, "onAuthenticationHelp() called with: helpCode = [" + helpCode + "], helpString = [" + helpString + "]");
            mDialog.setState(FingerDentifyDialog.STATE_FAILED,"");
            mManagerIdentifyCallback.onFailed();

        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.i(TAG, "onAuthenticationSucceeded: ");
            mDialog.setState(FingerDentifyDialog.STATE_SUCCEED,"");

            mManagerIdentifyCallback.onSucceeded();

        }
    }

    private FingerprintManager getFingerprintManager(Context context) {
        if (mFingerprintManager == null) {
            mFingerprintManager = context.getSystemService(FingerprintManager.class);
        }
        return mFingerprintManager;
    }

    public boolean isHardwareDetected() {
        if (mFingerprintManager != null) {
            return mFingerprintManager.isHardwareDetected();
        }
        return false;
    }

    public boolean hasEnrolledFingerprints() {
        if (mFingerprintManager != null) {
            return mFingerprintManager.hasEnrolledFingerprints();
        }
        return false;
    }
}
