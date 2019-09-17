package com.libs.ui.activity;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import com.libs.broadcastreceivers.GPSBroadcastReceiver;
import com.libs.broadcastreceivers.HomeBroadcastReceiver;
import com.libs.broadcastreceivers.LockScreenBroadcastReceiver;
import com.libs.broadcastreceivers.NetChangeBroadcastReceiver;
import com.libs.modle.listener.receiverListener.KOnGpsChangeListener;
import com.libs.modle.listener.receiverListener.KOnHomeListener;
import com.libs.modle.listener.receiverListener.KOnLockScreenListener;
import com.libs.modle.listener.receiverListener.KOnNetChangeListener;
import com.libs.modle.manager.KLocationManager;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.tipsUtil.ToastUtil;
import com.libs.view.dialog.IosAlertDialog;


/**
 * @ author：mo
 * @ data：2018/12/14
 * @ 功能：
 */
public class KReceiverActivity extends KPermissionsActivity implements KOnNetChangeListener,
        KOnGpsChangeListener, KOnHomeListener, KOnLockScreenListener {
    private GPSBroadcastReceiver gpsBroadcastReceiver;
    private NetChangeBroadcastReceiver netBroadcastReceiver;
    private HomeBroadcastReceiver homeBroadcastReceiver;
    private LockScreenBroadcastReceiver lockScreenBroadcastReceiver;

    /**
     * 网络类型
     */
    protected int netType;
    /**
     * Gps开启状态
     */
    protected Boolean isOpenGps;
    protected IosAlertDialog mGpsDialog;

    /**
     * 锁屏监听
     */
    protected void actionScreenListener() {
        lockScreenBroadcastReceiver = new LockScreenBroadcastReceiver(this);
    }

    /**
     * 开home键监听
     */
    protected void actionHomeListener() {
        homeBroadcastReceiver = new HomeBroadcastReceiver(this);
    }

    /**
     * 开网络监听
     */
    protected void actionNetListener() {
        netBroadcastReceiver = new NetChangeBroadcastReceiver(this);
    }

    /**
     * 开GPS监听
     */
    protected void actionGpsListener() {
        gpsBroadcastReceiver = new GPSBroadcastReceiver(this);
//        onGpsStatusChange(KLocationManager.INSTANCE.isOpen());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gpsBroadcastReceiver != null) {
            onGpsStatusChange(KLocationManager.INSTANCE.isOpen());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsBroadcastReceiver != null) {
            gpsBroadcastReceiver.onDestroy();
        }
        if (netBroadcastReceiver != null) {
            netBroadcastReceiver.onDestroy();
        }
        if (homeBroadcastReceiver != null) {
            homeBroadcastReceiver.onDestroy();
        }
        if (lockScreenBroadcastReceiver != null) {
            lockScreenBroadcastReceiver.onDestroy();
        }
        if (mGpsDialog != null) {
            mGpsDialog.dismiss();
        }
    }

    /**
     * 网络变化
     */
    @Override
    public void onNetChange(int netType) {
        this.netType = netType;
        if (netType == -1) {
            ToastUtil.showToast("网络异常，请检查网络连接");
        }
    }

    /**
     * GPS开关
     */
    @Override
    public void onGpsStatusChange(Boolean isOpen) {
        isOpenGps = isOpen;
        LogUtil.i("定位功能开启状态==" + isOpen);
        if (!isOpen) {
            mGpsDialog = new IosAlertDialog(mActivity).builder()
                    .setCancelable(false)
                    .setTitle("定位功能未开启")
                    .setMsg("请先打开GPS定位功能!")
                    .setPositiveButton("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            // 设置完成后返回到原来的界面
                            mActivity.startActivityForResult(intent, 0);
                        }
                    });
            mGpsDialog.show();
        } else {
            if (mGpsDialog != null) {
                mGpsDialog.dismiss();
            }

        }
    }

    /**
     * 点击home键
     *
     * @param status 1=短按Home键 只有这个是最靠谱的，
     *               2=长按Home键 或者 activity切换键
     *               3=锁屏
     *               4=samsung 长按Home键
     */
    @Override
    public void onHomeTouch(int status) {
    }

    /**
     * status;true=开屏
     * false=锁屏
     */
    @Override
    public void onScreenChange(Boolean status) {

    }
}
