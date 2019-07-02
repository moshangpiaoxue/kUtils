package com.libs.utils.systemUtils;

import android.os.Build;

/**
 * @ author：mo
 * @ data：2019/2/13:14:23
 * @ 功能：手机设备厂商相关
 */
public class ManufacturerUtil {

    private ManufacturerUtil() {
    }

    /**
     * 当前设备厂商
     */
    private static final Manufacturer CUR_MANUFACTURER = initManufacturer();

    /**
     * 初始化设备厂商
     *
     * @return 设备厂商
     */
    private static Manufacturer initManufacturer() {
        String device = Build.MANUFACTURER;
        Manufacturer curManufacturer = Manufacturer.OTHER;
        for (Manufacturer manufacturer : Manufacturer.values()) {
            if (manufacturer.isIt(device)) {
                curManufacturer = manufacturer;
                break;
            }
        }
        return curManufacturer;
    }

    /**
     * 获取当前设备的厂商
     *
     * @return 设备厂商
     */
    public static Manufacturer getManufacturer() {
        return CUR_MANUFACTURER;
    }

    /**
     * 设备厂商
     */
    public enum Manufacturer {
        /**
         *
         */
        HUAWEI("华为", "HUAWEI"),
        XIAOMI("小米", "Xiaomi"),
        SAMSUNG("三星", "samsung"),
        SONY("索尼", "Sony"),
        MEIZU("魅族", "Meizu"),
        OPPO("OPPO", "OPPO"),
        VIVO("vivo", "vivo"),
        LETV("乐视", "Letv"),
        ZTE("中兴", "ZTE"),
        YULONG("酷派", "Coolpad", "YuLong"),
        LENOVO("联想", "LENOVO"),
        LG("LG", "LG", "LGE"),
        SMARTISAN("锤子", "smartisan"),
        HTC("HTC", "HTC"),
        GIONEE("金立", "GIONEE"),
        _360("奇酷", "360"),
        MOTOROLA("摩托罗拉", "motorola"),
        NUBIA("努比亚", "nubia"),
        ONEPLUS("一加", "OnePlus"),

        Google("谷歌", "Google"),
        OTHER("其他", "other");

        private String mName;
        private String[] mManufacturers;

        Manufacturer(String name, String... manufacturers) {
            mName = name;
            mManufacturers = manufacturers;
        }

        public String getName() {
            return mName;
        }

        /**
         * 判断是否为当前设备厂商
         */
        boolean isIt(String manufacturer) {
            for (String manu : mManufacturers) {
                if (manu.equalsIgnoreCase(manufacturer)) {
                    return true;
                }
            }
            return false;
        }
    }
}
