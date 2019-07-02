package com.libs.modle.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Xml;

import com.libs.k;
import com.libs.utils.appUtils.PermissionUtil;
import com.libs.utils.dataUtil.StringUtil;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



/**
 * @ author：mo
 * @ data：2019/1/21
 * @ 功能：手机管理器
 */
public enum KTelephonyManager {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 获取手机管理器
     *
     * @return
     */
    public TelephonyManager getTelephonyManager() {
        return (TelephonyManager) k.app().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 是否是手机
     */
    public boolean isPhone() {
        return getTelephonyManager() != null && getTelephonyManager().getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 是否 root
     *
     * @return
     */
    public boolean isRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取移动终端类型
     *
     * @return 手机制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为 GSM，移动和联通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为 CDMA，电信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public int getPhoneType() {
        return getTelephonyManager() != null ? getTelephonyManager().getPhoneType() : -1;
    }

    /**
     * 获取 IMEI 码
     * 需添加权限;uses-permission android:name="android.permission.READ_PHONE_STATE"
     */
    @Nullable
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public String getIMEI() {
        return getTelephonyManager() != null ? getTelephonyManager().getDeviceId() : null;
    }

    /**
     * 唯一识别码，若果设备是手机，取EMEI码，否则取ANDROID ID
     */
    public String getOnlyCode() {
        return isPhone() ? getIMEI() : getAndroidId();
    }

    /**
     * ANDROID ID
     */
    @SuppressLint("HardwareIds")
    public String getAndroidId() {
        return Settings.Secure.getString(k.app().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取 IMSI 码
     * <需添加权限 uses-permission android:name="android.permission.READ_PHONE_STATE"
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public String getIMSI() {
        return getTelephonyManager() != null ? getTelephonyManager().getSubscriberId() : null;
    }


    /**
     * 判断 sim 卡是否准备好
     */
    public boolean isSimReady() {
        return getTelephonyManager() != null && getTelephonyManager().getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取 Sim 卡运营商名称 /;中国移动、如中国联通、中国电信
     */
    @Nullable
    public String getSimOperatorName() {
        return getTelephonyManager() != null ? getTelephonyManager().getSimOperatorName() : null;
    }

    /**
     * 获取 Sim 卡运营商名称 中国移动、如中国联通、中国电信
     */
    public String getSimOperatorByMnc() {
        TelephonyManager tm = getTelephonyManager();
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) {
            return null;
        }
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 设备的软件版本号
     */
    @SuppressLint("MissingPermission")
    public String getDeviceSoftwareVersion() {
        return getTelephonyManager().getDeviceSoftwareVersion();
    }

    /**
     * 手机号
     */
    @SuppressLint("HardwareIds")
    public String getLine1Number() {
        if (PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.READ_SMS)) {
            return getTelephonyManager().getLine1Number();
        }
        return "";
    }

    /**
     * ISO标准的国家码，即国际长途区号
     */
    public String getNetworkCountryIso() {
        return getTelephonyManager().getNetworkCountryIso();
    }

    /**
     * 设备的 MCC + MNC
     */
    public String getNetworkOperator() {
        return getTelephonyManager().getNetworkOperator();
    }

    /**
     * 获取(当前已注册的用户)的名字
     */
    public String getNetworkOperatorName() {
        return getTelephonyManager().getNetworkOperatorName();
    }

    /**
     * 获取当前使用的网络类型
     */
    public int getNetworkType() {
        return getTelephonyManager().getNetworkType();
    }


    /**
     * 获取SIM卡的国家码
     */
    public String getSimCountryIso() {
        return getTelephonyManager().getSimCountryIso();
    }

    /**
     * SIM网络码
     * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字
     */
    public String getSimOperator() {
        return getTelephonyManager().getSimOperator();
    }


    /**
     * 获取SIM卡的序列号
     */
    @SuppressLint("HardwareIds")
    public String getSimSerialNumber() {
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        return getTelephonyManager().getSimSerialNumber();
    }

    /**
     * SIM状态码
     */
    public int getSimState() {
        return getTelephonyManager().getSimState();
    }

    /**
     * 获取唯一的用户ID  IMSI
     */
    public String getSubscriberId() {
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        return getTelephonyManager().getSubscriberId();
    }

    /**
     * 获取语音邮件号码
     */
    public String getVoiceMailNumber() {
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        return getTelephonyManager().getVoiceMailNumber();
    }

    /**
     * 手机型号
     */
    public String getBuildBrandModel() {
        return Build.MODEL;
    }

    /**
     * 手机品牌
     */
    public String getBuildBrand() {
        return Build.BRAND;
    }

    /**
     * 手机系统版本号
     */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备厂商，如Xiaomi
     */
    public String getBuildMANUFACTURER() {
        return Build.MANUFACTURER;// samsung 品牌
    }

    /**
     * 序列号
     */
    public String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     */

    public String getMacAddress() {
        //方法1
//        String macAddress = null;
//        LineNumberReader lnr = null;
//        InputStreamReader isr = null;
//        try {
//            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
//            isr = new InputStreamReader(pp.getInputStream());
//            lnr = new LineNumberReader(isr);
//            macAddress = lnr.readLine().replace(":", "");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            FileUtil.closeIO(lnr, isr);
//        }
//        return macAddress == null ? "" : macAddress;
//        方法2
        WifiManager wifi = KConnectivityManager.INSTANCE.getWifiManager();
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            String macAddress = info.getMacAddress();
            if (macAddress != null) {
                return macAddress.replace(":", "");
            }
        }
        return null;
    }

    /**
     * 获取当前手机系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }


    /**
     * 获取手机联系人
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
     */
    public List<HashMap<String, String>> getAllContactInfo(Context context) {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"},
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!StringUtil.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[]{"data1",
                                "mimetype"}, "raw_contact_id=?",
                        new String[]{contact_id}, null);
                HashMap<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }


    /**
     * 获取手机短信并保存到 xml 中
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.READ_SMS" />}</p>
     */
    public void getAllSMS(Context context) {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取铃声音量，需要WRITE_APN_SETTINGS权限
     *
     * @param context 上下文
     * @return 铃声音量，取值范围为0-7；默认为0
     */
    public int getRingVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager.STREAM_RING);
    }

    /**
     * 获取媒体音量
     *
     * @param context 上下文
     * @return 媒体音量，取值范围为0-7
     */
    public void setRingVolume(Context context, int ringVloume) {
        if (ringVloume < 0) {
            ringVloume = 0;
        } else if (ringVloume > 7) {
            ringVloume = ringVloume % 7;
            if (ringVloume == 0) {
                ringVloume = 7;
            }
        }

        ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).setStreamVolume(AudioManager.STREAM_RING,
                ringVloume, AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 设置飞行模式的状态，需要WRITE_APN_SETTINGS权限
     *
     * @param context 上下文
     * @param enable  飞行模式的状态
     * @return 设置是否成功
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("deprecation")
    public boolean setAirplaneMode(Context context, boolean enable) {
        boolean result = true;
        // 如果飞行模式当前的状态与要设置的状态不一样
        if (isAirplaneModeOpen(context) != enable) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                result = Settings.System.putInt(context.getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
            } else {
                result = Settings.Global.putInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, enable ? 1 : 0);
            }
            // 发送飞行模式已经改变广播
            context.sendBroadcast(new Intent(
                    Intent.ACTION_AIRPLANE_MODE_CHANGED));
        }
        return result;
    }

    /**
     * 判断飞行模式是否打开，需要WRITE_APN_SETTINGS权限
     *
     * @param context 上下文
     * @return true：打开；false：关闭；默认关闭
     */
    public boolean isAirplaneModeOpen(Context context) {
        return getAirplaneModeState(context) == 1 ? true : false;
    }

    /**
     * 获取飞行模式的状态，需要WRITE_APN_SETTINGS权限
     *
     * @param context 上下文
     * @return 1：打开；0：关闭；默认：关闭
     */
    @SuppressWarnings("deprecation")
    public int getAirplaneModeState(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0);
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0);
        }
    }

}
