package com.libs.utils.dataUtil.dealUtil;


import com.libs.utils.ResUtil;
import com.libs.utils.systemUtils.ScreenUtil;

/**
 * @ author：mo
 * @ data：2019/2/15：9:43
 * @ 功能：单位转换工具类
 */
public class DensityUtil {
    private DensityUtil() {
        throw new UnsupportedOperationException("DensityUtil cannot be instantiated");
    }

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(float dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ScreenUtil.getDisplayMetrics());
        return (int) (dp * (ResUtil.getResource().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * dp 转 px
     *
     * @param dp dp 值
     * @return px 值
     */
    public static int dp2px(int dp) {
        return (int) (dp * (ResUtil.getResource().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static float px2dp(float px) {
        return (px / ScreenUtil.getDisplayMetrics().density);
    }

    /**
     * px 转 dp
     *
     * @param px px 值
     * @return dp 值
     */
    public static int px2dp(int px) {
        return (int) (px / (ResUtil.getResource().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param px px 值
     * @return dp 值
     */
    public static float px2sp(float px) {
//        return (px / ScreenUtil.getDisplayMetrics().scaledDensity);
        return (int) (px / (ResUtil.getResource().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param sp sp 值
     * @return px 值
     */
    public static int sp2px(float sp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp, ScreenUtil.getDisplayMetrics());
        return (int) (sp * (ResUtil.getResource().getDisplayMetrics().scaledDensity) + 0.5f);
    }
    /**
     * 十进制数值 转 十六进制字符串
     *
     * @param value 十进制数值
     * @return 十六进制字符串
     */
    public static String toHexString(int value) {
        return Integer.toHexString(value);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    /**
     * byte 数组 转 十六进制字符串
     *
     * @param bytes byte 数组
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
