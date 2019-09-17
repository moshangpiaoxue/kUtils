package com.libs.modle.unit;

/**
 * @ author：mo
 * @ data：2019/8/29:17:42
 * @ 功能：
 */
public class TimeUnit {
    /**
     * 秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;
    /**
     * 时间单位
     */
    public enum TimesUnit {
        /**
         * 毫秒
         */
        MSEC,
        /**
         * 秒
         */
        SEC,
        /**
         * 分
         */
        MIN,
        /**
         * 小时
         */
        HOUR,
        /**
         * 天
         */
        DAY
    }
}
