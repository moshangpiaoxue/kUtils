package com.libs.utils.dataUtil.stringUtils;

import java.math.BigDecimal;

/**
 * @ author：mo
 * @ data：2019/7/26:13:36
 * @ 功能：
 */
public class StringUtil2 {
    private volatile static StringUtil2 instance;

    /**
     * 第三种用法：双重检查 **推荐使用-既不浪费内存，性能相对也比较高**
     * iOS的通用写法
     */
    public static synchronized StringUtil2 getInstance() {
        if (instance == null) {
            //给这个类加锁
            synchronized (StringUtil2.class) {
                if (instance == null) {
                    instance = new StringUtil2();
                }
            }
        }
        return instance;
    }
    /**
     * 保留小数点后的几位
     *
     * @param in int
     * @param dd double
     * @return
     */
    public   String getString(int in, double dd) {
        //方法1：BigDecimal
        // BigDecimal.ROUND_HALF_UP表示四舍五入、BigDecimal.ROUND_HALF_DOWN也是五舍六入、
        // BigDecimal.ROUND_UP表示进位处理（就是直接加1）、BigDecimal.ROUND_DOWN表示直接去掉尾数
         return (new BigDecimal(dd)).setScale(in, BigDecimal.ROUND_DOWN).doubleValue()+"";

        //方法2：DecimalFormat
//        String pattern = "#.";
//        for (int i = 0; i < in; i++) {
//            pattern += "0";
//        }
//        return new DecimalFormat(pattern).format(dd);

        //方法3：%.2f表示保留后两位，能四舍五入
//        return String.format("%." + in + "f", dd);

    }
}
