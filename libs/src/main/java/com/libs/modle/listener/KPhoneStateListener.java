package com.libs.modle.listener;

import android.telephony.PhoneStateListener;

/**
 * @ author：mo
 * @ data：2018/12/11
 * @ 功能：手机状态监听
 */
public class KPhoneStateListener extends PhoneStateListener {
    /**
     * 通话状态变化
     *
     * @param state          TelephonyManager.CALL_STATE_IDLE=电话空闲
     *                       TelephonyManager.CALL_STATE_RINGING=响铃中
     *                       TelephonyManager.CALL_STATE_OFFHOOK=通话中
     * @param incomingNumber
     */
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
    }

    /**
     * 网络状态变化
     *
     * @param state TelephonyManager.DATA_CONNECTED=已连接
     *              TelephonyManager.DATA_CONNECTING=正在连接
     *              TelephonyManager.DATA_DISCONNECTED=已关闭
     */
    @Override
    public void onDataConnectionStateChanged(int state) {
        super.onDataConnectionStateChanged(state);
    }

    /**
     * 数据活动
     *
     * @param direction TelephonyManager.DATA_ACTIVITY_IN=下载
     *                  TelephonyManager.DATA_ACTIVITY_OUT=上传
     *                  TelephonyManager.DATA_ACTIVITY_INOUT上传/下载
     *                  TelephonyManager.DATA_ACTIVITY_NONE=未知
     */
    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
    }
}
