package com.libs.utils.dataUtil.date;

import com.libs.utils.tipsUtil.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * @ author：mo
 * @ data：2019/2/15:11:33
 * @ 功能：DateFormat工具类
 */
public class DateFormatUtil {
    /**
     * 判断日期格式是否正确
     *
     * @param formatStr 日期格式 如："yyyy-MM-dd"
     * @return true=格式正确 false=格式不正确
     */
    public static Boolean isDateFormat(String formatStr) {
        Boolean boo = false;
        try {
            new SimpleDateFormat(formatStr);
            boo = true;
        } catch (Exception e) {
            boo = false;
            ToastUtil.showToast("格式不正确，使用默认格式！");
        }
        return boo;
    }

    /**
     * 获取时间格式
     *
     * @return 默认时间格式："yyyy-MM-dd"
     */
    public static SimpleDateFormat getSDF() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
    /**
     * 获取时间格式
     *
     * @param formatStr 自定义时间格式样式 如 " yyyy-MM-dd HH:mm:ss "   或   yyyy年MM月dd日   yyyy是完整的公元年，MM是月份，dd是日期，
     *                  备注:获取12小时制的时间要把HH换成小写hh，默认是大写的24小时制的
     * @return 定义时间格式
     */
    public static SimpleDateFormat getSDF(String formatStr) {
        if (isDateFormat(formatStr)) {
            return new SimpleDateFormat(formatStr, Locale.getDefault());
        } else {
            return getSDF();
        }
    }
}
