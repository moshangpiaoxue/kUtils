package com.libs.modle.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.libs.k;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
import static android.telephony.TelephonyManager.PHONE_TYPE_GSM;
import static android.telephony.TelephonyManager.PHONE_TYPE_NONE;

/**
 * @ author：mo
 * @ data：2019/1/21
 * @ 功能：弯路连接管理器
 */
public enum KConnectivityManager {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 获取ConnectivityManager
     */
    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) k.app().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * wifi管理器
     */
    public WifiManager getWifiManager() {
        return (WifiManager) k.app().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取网络信息
     * 需添加权限:<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    public NetworkInfo getNetworkInfo() {
        return ((ConnectivityManager) k.app().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 手机信号类型
     * 常用话机类型就是GSM类型和CDMA类型，SIP是和VOIP相关的东西，平时不常遇到。
     */
    public String getPhoneSignalType() {
        switch (KTelephonyManager.INSTANCE.getPhoneType()) {
            case PHONE_TYPE_NONE:
                return "无信号";
            case PHONE_TYPE_GSM:
                return "GSM信号";
            case PHONE_TYPE_CDMA:
                return "CDMA信号";
            default:
                return "";
        }
    }

    /**
     * 网络类型：
     * -1=没网
     * 2=2G
     * 3=3G
     * 4=4G
     * 18=WiFi
     * 其他不用管
     */
    public int getNetworkType() {
        NetworkInfo networkInfo = getNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            /** 没有任何网络 */
            return -1;
        }
//        if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
//            /** 以太网网络 */
//            return 2;
//        } else
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return 18;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String _strSubTypeName = networkInfo.getSubtypeName();
            switch (networkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    /** 2G网络 */
                    return 2;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    /** 3G网络 */
                    return 3;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    /** 4G网络 */
                    return 4;
                default:
                    // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                    if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                        return 3;
                    } else {
                        return networkInfo.getSubtype();
                    }
            }
        }
        /** 没连接 */
        return -1;
    }

    /**
     * 网络类型
     */
    public String getNetworkType2() {
        switch (getNetworkType()) {
            case -1:
                return "没网";
            case 2:
                return "2G";
            case 3:
                return "3G";
            case 4:
                return "4G";
            case 18:
                return "wifi";
            default:
                return "有网但是不知道叫啥。。";
        }
    }

    /**
     * 网络是否连接
     */
    public boolean isConnected() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isConnected();
//        return info != null && info.isConnected()&&isNetworkOnline();
    }

    /**
     * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
     *
     * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
     */
    public boolean isConnectedOrConnecting() {
        NetworkInfo[] nets = getConnectivityManager().getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 移动数据是否打开
     */
    public boolean isMobileDataOpen() {
        try {
//            TelephonyManager tm = PhoneUtil.getTelephonyManager();
//            if (tm == null) return false;
//            @SuppressLint("PrivateApi")
//            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
//            if (null != getMobileDataEnabledMethod) {
//                return (boolean) getMobileDataEnabledMethod.invoke(tm);
//            }

            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(getConnectivityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 是否是移动数据
     * <p>需添加权限
     */
    @SuppressLint("MissingPermission")
    public boolean isMobileData() {
        NetworkInfo info = getNetworkInfo();
        return null != info && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断网络是否是 4G
     */
    public boolean is4G() {
        return getNetworkType() == 4;
    }

    /**
     * wifi 是否连接状态
     */
    public boolean isWifi() {
        return getNetworkType() == 18;
    }

    /**
     * wifi 是否打开
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isWifiOpen() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = getWifiManager();
        return manager != null && manager.isWifiEnabled();
    }


    /**
     * 判断 wifi 数据是否可用
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isWifiAvailable() {
        return isWifiOpen();
//        NetworkInfo[] nets = getConnectivityManager().getAllNetworkInfo();
//        if (nets != null) {
//            for (NetworkInfo net : nets) {
//                if (net.getType() == ConnectivityManager.TYPE_WIFI) {
//                    return net.isAvailable();
//                }
//            }
//        }
//        return false;
    }


    /**
     * 获取 IP 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     */
    public String getIPAddress(final boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis =
                 NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回 10.0.2.15
                if (!ni.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) {
                                return hostAddress;
                            }
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0
                                        ? hostAddress.toUpperCase()
                                        : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取域名 ip 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain 域名
     */
    public String getDomainAddress(final String domain) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 网络是否可用
     */
    public boolean isNetworkOnline() {
        try {
            if (InetAddress.getByName("120.25.236.134").isReachable(3000)) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            return false;
        }
    }
}
