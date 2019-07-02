package com.libs.modle.constants;

import android.Manifest;

/**
 * @ author：mo
 * @ data：2018/7/29：14:42
 * @ 功能：权限组常量
 * @ 说明：6.0权限的基本知识，以下是需要单独申请的权限，共分为9组，每组只要有一个权限申请成功了，就默认整组权限都可以使用了。
 */
public class ConstansePermissionGroup {
    /**
     * sd卡权限组
     * 测试申请读卡即可，如果放开写入权限，不会报错但是写入权限会申请失败
     */
    public static String[] PERMISSIONS_STORAGE = {
            //写入
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //读卡
            Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 联系人权限组
     */
    public static String[] PERMISSIONS_CONTACTS = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS
    };
    /**
     * 通讯权限组
     */
    public static String[] PERMISSIONS_PHONE = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL
    };
    /**
     * SMS卡权限组
     */
    public static String[] PERMISSIONS_SMS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
//            Manifest.permission.READ_CELL_BROADCASTS
    };
    /**
     * 传声器权限组
     */
    public static String[] PERMISSIONS_MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };
    /**
     * 定位权限组
     */
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    /**
     * 传感器权限组
     */
    public static String[] PERMISSIONS_SENSORS = {
            Manifest.permission.BODY_SENSORS
    };
    /**
     * 相机权限组
     */
    public static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };
    /**
     * 日历权限组
     */
    public static String[] PERMISSIONS_CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };
    /**
     * 录像权限组
     */
    public static String[] PERMISSIONS_VIDEO_TAPE = {
            Manifest.permission.CAMERA,
    };

}
