package com.libs.utils.dataUtil.date;

import com.libs.modle.constants.ConstUtil;
import com.libs.modle.unit.TimeUnit;
import com.libs.utils.dataUtil.LongUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.libs.modle.unit.TimeUnit.DAY;
import static com.libs.modle.unit.TimeUnit.HOUR;
import static com.libs.modle.unit.TimeUnit.MIN;
import static com.libs.modle.unit.TimeUnit.MSEC;
import static com.libs.modle.unit.TimeUnit.SEC;
import static com.libs.utils.dataUtil.date.CalendarUtil.getCalendar;
import static com.libs.utils.dataUtil.date.DateFormatUtil.getSDF;

/**
 * @ author：mo
 * @ data：2019/2/15:11:03
 * @ 功能：日期格式的转换工具类
 * <p>
 * <p>
 * <p>
 * format 格式 如 "yyyy-MM-dd HH:mm:ss"   或   yyyy年MM月dd日   yyyy是完整的公元年，MM是月份，dd是日期
 * * 备注:获取12小时制的时间要把HH换成小写hh，默认是大写的24小时制的
 * G：年代标识，表示是公元前还是公元后
 * y：年份
 * M：月份
 * d：日
 * h：小时，从1到12，分上下午
 * H：小时，从0到23
 * m：分钟
 * s：秒
 * S：毫秒
 * E：一周中的第几天，对应星期几，第一天为星期日，于此类推
 * z：时区
 * D：一年中的第几天
 * F：这一天所对应的星期几在该月中是第几次出现
 * w：一年中的第几个星期
 * W：一个月中的第几个星期
 * a：上午/下午标识
 * k：小时，从1到24
 * K：小时，从0到11，区分上下午
 * 输入的个数就是现实的位数
 */
public class DateUtil {
    /**
     * 是否闰年
     *
     * @param year 年份
     * @return true 闰年、false 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @return true今天 false不是
     */
    public static boolean isToday(long day) {
        return isOneDay(0, day);
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param time 传入的 时间戳 秒级 毫秒级都可以
     * @return true今天 false不是
     */
    public static boolean isYesterday(long time) {
        Calendar pre = getCalendar(0);
        Calendar cal = getCalendar(time);
        return cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR)) && cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR) == -1;
    }

    /**
     * 判断是否为今年
     *
     * @param time 传入的 时间戳 秒级 毫秒级都可以
     * @return true今年 false不是
     */
    public static boolean isThisYear(long time) {
        return isOneYear(0, time);
    }

    /**
     * 判断是否同一年
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneYear(long time, long time2) {
        return getCalendar(time).get(Calendar.YEAR) == (getCalendar(time2).get(Calendar.YEAR));
    }

    /**
     * 判断是否同一天
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneDay(long time, long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        return one.get(Calendar.YEAR) == (two.get(Calendar.YEAR)) && two.get(Calendar.DAY_OF_YEAR) == one.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断两个日期是否相连(不能跨年)
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isAdiacentDay(Long time, Long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        if (one.get(Calendar.YEAR) == (two.get(Calendar.YEAR)) && one.get(Calendar.MONTH) == (two.get(Calendar.MONTH))) {
            return two.get(Calendar.DAY_OF_YEAR) - one.get(Calendar.DAY_OF_YEAR) == 1 || two.get(Calendar.DAY_OF_YEAR) - one.get(Calendar.DAY_OF_YEAR) == -1 ? true : false;
        }
        return false;
    }

    /**
     * 判断是否同一月
     * 不能跨年
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneMonth(Long time, Long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        return one.get(Calendar.YEAR) == (two.get(Calendar.YEAR)) && two.get(Calendar.MONTH) == one.get(Calendar.MONTH);
    }

    /**
     * 毫秒时间戳  转  毫秒、秒、分、小时、天单位的时间戳
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         要转换成的单位
     * @return unit时间戳
     */
    private static long ms2Unit(long milliseconds, TimeUnit.TimesUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
            default:
                break;
        }
        return -1;
    }

    /**
     * unit毫秒、秒、分、小时、天时间戳   转   毫秒时间戳
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         要转换成的单位
     * @return unit时间戳
     */
    private static long unit2Ms(long milliseconds, TimeUnit.TimesUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds * MSEC;
            case SEC:
                return milliseconds * SEC;
            case MIN:
                return milliseconds * MIN;
            case HOUR:
                return milliseconds * HOUR;
            case DAY:
                return milliseconds * DAY;
            default:
                break;
        }
        return -1;
    }

    /**
     * 获取当前秒级时间戳
     *
     * @return 10位时间戳
     */
    public static long getM() {
        return ms2Unit(getMS(), TimeUnit.TimesUnit.SEC);
    }

    /**
     * 获取当前毫秒级时间戳
     *
     * @return 13位时间戳
     */
    public static long getMS() {
        return System.currentTimeMillis();
    }

    /**
     * 检验时间戳，默认输出毫秒级时间戳，如果格式不对，输出当前毫秒级时间戳
     *
     * @param time
     * @return
     */
    public static long getMS(long time) {
        int size = LongUtil.getLongSize(time);
        if (size == 10) {
            return time * 1000;
        } else if (size == 13) {
            return time;
        } else {
            return getMS();
        }
    }

    /**
     * 获取时间戳  Date  转  时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long getMS(Date time) {
        return time.getTime();
    }


    /**
     * 获取当前日期String
     *
     * @return 默认日期格式："yyyy-MM-dd"
     */
    public static String getString() {
        return getString(getMS(), getSDF());
    }


    /**
     * 获取当前日期String
     *
     * @param formatStr 自定义日期格式
     * @return 自定义时间格式的当前日期
     */
    public static String getString(String formatStr) {
        return getString(getMS(), getSDF(formatStr));
    }

    /**
     * 获取日期String   毫秒时间戳-->>时间字符串
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String getString(long milliseconds) {
        return getString(getMS(milliseconds), getSDF());
    }

    /**
     * 获取日期String  时间戳-->>时间字符串
     *
     * @param milliseconds 毫秒时间戳
     * @param formatStr    自定义时间格式
     * @return
     */
    public static String getString(long milliseconds, String formatStr) {
        return getString(getMS(milliseconds), getSDF(formatStr));
    }

    /**
     * 获取日期String      时间戳-->>时间字符串
     *
     * @param milliseconds 时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String getString(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 获取日期String    Date类型-->>时间字符串
     *
     * @param time Date类型时间
     * @return 默认格式时间字符串
     */
    public static String getString(Date time) {
        return getString(time, getSDF());
    }

    /**
     * 获取日期String  Date类型-->>时间字符串
     *
     * @param time      Date类型时间
     * @param formatStr 时间格式
     * @return 时间字符串
     */
    public static String getString(Date time, String formatStr) {
        return getString(time, getSDF(formatStr));
    }

    /**
     * 获取日期String  Date类型-->>时间字符串
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getString(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 获取当前时间
     *
     * @return Date类型的当前时间
     */
    public static Date getData() {
        return new Date();
    }

    /**
     * 获取日期Date   时间戳-->>Date类型
     *
     * @param milliseconds 时间戳
     * @return Date类型时间
     */
    public static Date getDate(long milliseconds) {
        return new Date(getMS(milliseconds));
    }

    /**
     * 获取日期Date  时间字符串-->>Date类型
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date getDate(String time) {
        return getDate(time, getSDF());
    }

    /**
     * 获取日期Date   时间字符串-->>Date类型
     *
     * @param time      时间字符串
     * @param formatStr 时间格式
     * @return
     */
    public static Date getDate(String time, String formatStr) {
        return getDate(time, getSDF(formatStr));
    }

    /**
     * 获取日期Date  时间字符串-->>Date类型
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date getDate(String time, SimpleDateFormat format) {
        return new Date(getMS(time, format));
    }

    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long getMS(String time) {
        return getMS(time, getSDF());
    }


    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time      时间字符串
     * @param formatStr 时间格式
     * @return
     */
    public static long getMS(String time, String formatStr) {
        return getMS(time, getSDF(formatStr));
    }

    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long getMS(String time, SimpleDateFormat format) {
        try {
            String re_time = null;
            Date d;
            d = format.parse(time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


    //////////////////////////////////////////////////////////////////////  Day  天  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取两个时间戳的间隔天数
     *
     * @param ms1 时间戳
     * @param ms2 时间戳
     * @return 间隔天数
     */
    public static int getDays(long ms1, long ms2) {
        return Math.abs((int) (getMS(ms1) / 1000 - getMS(ms2) / 1000) / (60 * 60 * 24));
    }

    /**
     * 获取某年某月的天数
     *
     * @param year  年份
     * @param month 月
     * @return 天数
     */
    public static int getDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 距离现在多长时间
     *
     * @param timeStr 传入的毫秒数
     * @return 返回类型有多少月前，多少天前，多少小时前，多少分钟前
     */
    public static String getDateAgo(String timeStr) {
        String temp = "";
        try {
            long now = System.currentTimeMillis() / 1000;
            long publish = Long.parseLong(timeStr);
            long diff = now - publish;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
            if (months > 0) {
                temp = months + "月前";
            } else if (days > 0) {
                temp = days + "天前";
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else if (minutes > 0) {
                temp = minutes + "分钟前";
            } else {
                temp = "刚刚";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 获取前n天或后n天的日期
     *
     * @param days   天数  几天前/后 负数是前，正数是后
     * @param format 返回日期格式
     * @return days天前后的日期
     */
    public static String getDateString(int days, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return getSDF(format).format(calendar.getTime());
    }
//////////////////////////////////////////////////////////////////////  Week 星期  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * data  转  星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        return getWeek(getMS(date));
    }

    /**
     * 时间戳  转  星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(long date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = getCalendar(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 获取今天是这个月的第几周
     *
     * @return 第几周
     */
    public static int getWeekOfMonth() {
        return CalendarUtil.getWeekOfMonth();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取自定义样式时间串
     *
     * @param milliseconds 时间戳
     * @return 自定义样式时间串
     */
    public static String getCustomDate(long milliseconds) {
        String returnSre = "";
        if (DateUtil.isThisYear(milliseconds)) {
            if (DateUtil.isToday(milliseconds)) {
                returnSre = "今天 " + DateUtil.getString(milliseconds, "HH:mm");
            } else if (DateUtil.isYesterday(milliseconds)) {
                returnSre = "昨天 " + DateUtil.getString(milliseconds, "HH:mm");
            } else {
                returnSre = DateUtil.getString(milliseconds, "MM-dd HH:mm");
            }
        } else {
            returnSre = DateUtil.getString(milliseconds, "yyyy-MM-dd HH:mm");
        }
        return returnSre;
    }

    /**
     * 得到月日对应的星座
     *
     * @param month 月份
     * @param day   天
     * @return 返回星座信息
     */
    public static String getConstellation(int month, int day) {
        String[] astro = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
                "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] arr = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};// 两个星座分割日
        if (month <= 0 || day <= 0) {
            return "猴年马月座";
        } else if (month > 12 || day > 31) {
            return "猴年马月座";
        }
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < arr[month - 1]) {
            index = index - 1;
        }
        // 返回索引指向的星座string
        return astro[index];
    }


}
