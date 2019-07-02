package com.libs.utils.dataUtil;

import java.math.BigDecimal;

/**
 * @ author：mo
 * @ data：2019/2/13:17:51
 * @ 功能：BigDecimal 精确计算工具类
 */
public class BigDecimalUtil {
    /**
     * 用double类型的数据作为参数时，生成数据可能不准确，所以都用string当参数生成
     */
    public static BigDecimal getBigDecimal(Object obj) {
        if (ObjectUtil.isEmpty(obj)) {
            return new BigDecimal("0");
        } else if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
//        if (obj instanceof Integer) {
//            return new BigDecimal((Integer) obj);
//        } else if (obj instanceof char[]) {
//            return new BigDecimal((char[]) obj);
//        } else if (obj instanceof Double) {
//            return new BigDecimal((Double) obj);
//        } else if (obj instanceof String) {
//            return new BigDecimal((String) obj);
//        } else if (obj instanceof BigInteger) {
//            return new BigDecimal((BigInteger) obj);
//        } else if (obj instanceof Long) {
//            return new BigDecimal((Long) obj);
//        }
        else {
            return new BigDecimal(String.valueOf(obj));
        }
    }

    /**
     * 四舍五入
     * @param obj 数据
     * @param digit 保留小数位
     * @return
     */
    public static BigDecimal getRoundUp(Object obj, int digit) {
        return getRoundUp(getBigDecimal(obj), digit);
    }
    /**
     * 四舍五入
     * @param bigDecimal 数据
     * @param digit 保留小数位
     * @return
     */
    public static BigDecimal getRoundUp(BigDecimal bigDecimal, int digit) {
        return bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 获取百分比（乘100）
     *
     * @param obj 数值
     * @param digit 保留小数位
     * @return
     */
    public static BigDecimal getPercentValue(Object obj, int digit) {
       return  getRoundUp(getBigDecimal(obj).multiply(new BigDecimal(100)), digit);
    }
}
