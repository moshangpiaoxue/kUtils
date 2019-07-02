package com.libs.utils.dataUtil;

import java.text.DecimalFormat;

/**
 * @ author：mo
 * @ data：2019/4/1:10:09
 * @ 功能：地图相关工具类
 */
public class MapGeographicUtil {
    public static double EARTH_RADIUS = 6371.393;

    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    /**
     * 格式化距离，千米
     *
     * @param distance
     * @return
     */

    public static String formatDistance1(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        distance /= 1000;
        return decimalFormat.format(distance) + "km";
    }

    /**
     * 格式化距离，千米
     *
     * @param distance
     * @return
     */

    public static String formatDistance(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        distance /= 1000;
        return decimalFormat.format(distance);
    }

    /**
     * 格式化距离，当小于三位写米，大于三位写千米
     *
     * @param distance
     * @return
     */

    public static String formatDistance0(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (distance >= 1000) {
            distance /= 1000;
            return decimalFormat.format(distance) + "千米";
        }
//        Math.round(distance);
        return decimalFormat.format(distance) + "米";
    }
}
