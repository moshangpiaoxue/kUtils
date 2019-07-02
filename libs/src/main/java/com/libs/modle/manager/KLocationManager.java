package com.libs.modle.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.libs.k;

import java.util.List;



/**
 * @ author：mo
 * @ data：2019/1/21
 * @ 功能：定位管理器
 */
public enum KLocationManager {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 获取管理器
     */
    public LocationManager getLocationManager() {
        return (LocationManager) k.app().getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * GPS是否开启
     *
     * @return
     */
    public Boolean isOpen() {
        return getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public boolean isOpen2() {
        LocationManager locationManager = (LocationManager) k.app().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        if (isOpen() || getLocationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }
        return false;
    }

    public void checkGPS(final Activity mActivity) {
        // 判断GPS模块是否开启，如果没有则开启
        if (!isOpen()) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
            dialog.setTitle("定位功能未开启");
            dialog.setMessage("请先打开GPS定位功能!");
            dialog.setCancelable(false);
            dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    // 设置完成后返回到原来的界面
                    mActivity.startActivityForResult(intent, 0);
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    /**
     * 获取经纬度
     *
     * @return
     */
    public String getLngAndLat(final Activity mActivity, OnLocationResultListener onLocationResultListener) {
        //监视地理位置变化
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        double latitude = 0.0;
        double longitude = 0.0;
        mOnLocationListener = onLocationResultListener;

        String locationProvider = null;
        LocationManager locationManager = getLocationManager();
        //获取所有可用的位置提供器
        List<String> providers = getLocationManager().getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            k.app().startActivity(i);
            return null;
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            if (onLocationResultListener != null) {
                onLocationResultListener.onLocationResult(location);
            }

        }

        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        return null;
    }


    public LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (mOnLocationListener != null) {
                mOnLocationListener.OnLocationChange(location);
            }
        }
    };

    public void removeListener() {
        getLocationManager().removeUpdates(locationListener);
    }

    private OnLocationResultListener mOnLocationListener;

    public interface OnLocationResultListener {
        void onLocationResult(Location location);

        void OnLocationChange(Location location);
    }

}
