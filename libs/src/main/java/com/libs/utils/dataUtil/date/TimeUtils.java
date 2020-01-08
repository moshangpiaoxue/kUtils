package com.libs.utils.dataUtil.date;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @ author：mo
 * @ data：2019/2/13:18:33
 * @ 功能：时间格式的转换工具类
 */
public class TimeUtils {

    private TimeUtils() {
    }
    /**
     * 获取秒数    秒时间戳---》》秒数
     *
     * @param m 时间戳
     * @return 秒数
     */
    public static long getSeconds(long m) {
        return new BigDecimal((float) (DateUtil.getMS(m)/1000)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }
    /**
     * 获取秒数  时分秒 --》》 秒数
     *
     * @param time "hh:mm:ss"或"mm:ss" 只能是以：冒号格式的时间
     * @return 秒数
     */
    public static long getSeconds(String time) {
        String s = time;
        int hh = 0;
        int mi = 0;
        int ss = 0;
        //首次出现：的位置
        int index1 = s.indexOf(":");
        if (index1 == -1) {
            ss = Integer.parseInt(time);
        } else {
            //第二次出现：的位置
            int index2 = s.indexOf(":", index1 + 1);
            //如果第二次的位置为-1，说明这个时间是 分：秒 mm:ss 格式  没有小时
            if (index2 == -1) {
                mi = Integer.parseInt(s.substring(0, index1));
                ss = Integer.parseInt(s.substring(index1 + 1));
                //如果第二次的位置为正值，说明这个时间是 时：分：秒 hh：mm:ss 格式  有小时
            } else {
                hh = Integer.parseInt(s.substring(0, index1));
                mi = Integer.parseInt(s.substring(index1 + 1, index2));
                ss = Integer.parseInt(s.substring(index2 + 1));
            }
        }
        return hh * 60 * 60 + mi * 60 + ss;
    }
    /**
     * 获取时长  秒数 --》》 时分秒
     *
     * @param seconds 秒数
     * @return 1小时20分30秒
     */
    public static String getDurationFromSecond(int seconds) {
        return getDurationFromSecond(seconds, "小时", "分", "秒");
    }
    /**
     * 根据分钟获取"XX小时XX分钟"格式时间
     *
     * @param time
     * @return
     */
    public static String getTimeForMin(double time) {
        //向上取整
        int aaa = (int) Math.ceil(time);
        int bbb = aaa / 60;
        int ccc = aaa % 60;
        if (bbb > 0) {
            return bbb + "小时" + ccc + "分钟";
        } else {
            return ccc + "分钟";
        }

    }
    /**
     * 获取时长   秒数 --》》 时分秒
     *
     * @param seconds       秒数
     * @param hourSuffix    小时后面的后缀
     * @param minuteSuffix  分后面的后缀
     * @param hsecondSuffix 秒后面的后缀
     * @return 1小时20分30秒 或 1：20：30
     */
    public static String getDurationFromSecond(int seconds, String hourSuffix, String minuteSuffix, String hsecondSuffix) {
        StringBuilder sb = new StringBuilder();
        if (seconds < 1) {
            seconds = 0;
        }
        //几小时
        int hour = seconds / (60 * 60);
        //几分
        int minute = (seconds - 60 * 60 * hour) / 60;
        //当小时不为0，时分都显示
        if (hour != 0) {
            sb.append(hour).append(hourSuffix);
            sb.append(minute).append(minuteSuffix);
            //当小时为0
        } else {
            //当分不等于0，展示分，否则不展示
            if (minute != 0) {
                sb.append(minute).append(minuteSuffix);
            }
        }
        //几秒
        int second = (seconds - 60 * 60 * hour - 60 * minute);
        sb.append(second).append(hsecondSuffix);
        return sb.toString();
    }

    /**
     * 获取时长  毫秒  转  16:40样式
     * @param timeInMillis  毫秒
     * @return  16:40样式时长
     */
    public static String getDurationFromMillisecond(long timeInMillis) {
        long hour = timeInMillis / (60 * 60 * 1000);
        long minute = (timeInMillis - hour * 60 * 60 * 1000) / (60 * 1000);
        long second = (timeInMillis - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        if (second >= 60) {
            second = second % 60;
            minute += second / 60;
        }
        if (minute >= 60) {
            minute = minute % 60;
            hour += minute / 60;
        }
        String sh = "";
        String sm = "";
        String ss = "";
        if (hour < 10) {
            sh = "0" + String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = "0" + String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (second < 10) {
            ss = "0" + String.valueOf(second);
        } else {
            ss = String.valueOf(second);
        }
        return sm + ":" + ss;
    }


    /**
     * 将以前的时间毫秒值和现在对比，转化成描述性时间字符串，并附上当时的时间后缀
     *
     * @param beforeDate 当前时间点以前的时间
     * @return 描述性时间字符串 如：下午 15:26
     */
    public static String getTime(Date beforeDate, boolean isShowWeek) {
        long milliseconds = beforeDate.getTime();
        SimpleDateFormat formatBuilder = new SimpleDateFormat("HH:mm");
        String time = formatBuilder.format(milliseconds);
        StringBuffer sb = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.ceil(datetime / 24 / 60 / 60 / 1000.0f); // 天前 (向上取整)

        if (day <= 7) { // 一周内
            if (day == 0) { // 今天
                if (hour <= 4) {
                    sb.append("凌晨 ");
                } else if (hour > 4 && hour <= 6) {
                    sb.append("早上 ");
                } else if (hour > 6 && hour <= 11) {
                    sb.append("上午 ");
                } else if (hour > 11 && hour <= 13) {
                    sb.append("中午 ");
                } else if (hour > 13 && hour <= 18) {
                    sb.append("下午 ");
                } else if (hour > 18 && hour <= 19) {
                    sb.append("傍晚 ");
                } else if (hour > 19 && hour <= 24) {
                    sb.append("晚上 ");
                } else {
                    sb.append("今天 ");
                }
            } else if (day == 1) { // 昨天
                sb.append("昨天 ");
            } else if (day == 2) { // 前天
                sb.append("前天 ");
            } else {
                sb.append(DateUtil.getWeek(milliseconds));
            }
            // 添加后缀
            sb.append(time);
        } else if (day <= 30 && isShowWeek) { // 一周之前
            sb.append(day % 7 == 0 ? (day / 7) : (day / 7 + 1)).append("周前");
        } else { // 一个月之前
            sb.append(new SimpleDateFormat("MM月dd日 ", Locale.getDefault()).format(beforeDate)).append(time);
        }
        return sb.toString();
    }

    /**
     * 将指定的时间和当前时间对比出来的时间间隔转换成描述性字符串，如2天前，3月1天后等。
     *
     * @param toDate 相对的日期
     * @param isFull 是否全部显示： true 全部显示，如x年x月x日x时x分后； false 简单显示
     * @return 将时间间隔转换成描述性字符串，如2天前，3月1天后等。
     */
    public static String getTime2(Date toDate, boolean isFull) {
        String diffDesc = "";
        String fix = "";
        Long diffTime;
        Date curDate = new Date();
        if (curDate.getTime() > toDate.getTime()) {
            diffTime = curDate.getTime() - toDate.getTime();
            fix = "前";
        } else {
            diffTime = toDate.getTime() - curDate.getTime();
            fix = "后";
        }

        //换算成分钟数，防止Int溢出。
        diffTime = diffTime / 1000 / 60;
        Long year = diffTime / (60 * 24 * 30 * 12);
        diffTime = diffTime % (60 * 24 * 30 * 12);
        if (year > 0) {
            diffDesc = diffDesc + year + "年";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long month = diffTime / (60 * 24 * 30);
        diffTime = diffTime % (60 * 24 * 30);
        if (month > 0) {
            diffDesc = diffDesc + month + "月";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long day = diffTime / 60 / 24;
        diffTime = diffTime % (60 * 24);
        if (day > 0) {
            diffDesc = diffDesc + day + "天";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long hour = diffTime / (60);
        diffTime = diffTime % (60);
        if (hour > 0) {
            diffDesc = diffDesc + hour + "小时";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        Long minitue = diffTime;
        if (minitue > 0) {
            diffDesc = diffDesc + minitue + "分钟";
            if (!isFull) {
                return diffDesc + fix;
            }
        }
        return diffDesc + fix;
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     */
    public static String getTime(long timeLong) {
        return getTime(timeLong, true, 0,true);
    }
    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param isFull   是否全部显示： true 全部显示，如x年x月x日x时x分； false 简单显示,如4月 / 3天
     */
    public static String getTime(long timeLong, boolean isFull) {
        return getTime(timeLong, isFull, 0,true);
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如 3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param minUnit  最低显示单位, 6->年 5->月 4->日 3->时 2->分, 如3: 00时00分06秒
     */
    public static String formatTimeLongCn(long timeLong, int minUnit) {
        return getTime(timeLong, true, minUnit,true);
    }

    /**
     * 将指定的时间长度转换成描述性字符串，如2天，3月1天12时5分4秒。
     *
     * @param timeLong 相对的日期
     * @param isFull   是否全部显示： true 全部显示，如x年x月x日x时x分； false 简单显示,如4月 / 3天
     * @param minUnit  最低显示单位, 6->年 5->月 4->日 3->时 2->分, 如3: 00时00分06秒
     * @param isUp      换算秒数在出现小数位的情况下，是否向上取整
     */
    public static String getTime(long timeLong, boolean isFull, int minUnit,boolean isUp) {
        StringBuilder diffDesc = new StringBuilder();

        //换算秒，防止Int溢出,向上取整
//        timeLong = Math.round((double)( timeLong )/ 1000);
        //向下取，抹掉小数位的秒
//        timeLong = (long)Math.floor((double)( timeLong )/ 1000);
        timeLong=isUp?Math.round((double)( timeLong )/ 1000):(long)Math.floor((double)( timeLong )/ 1000);
        Long year = timeLong / (60 * 60 * 24 * 30 * 12);
        timeLong = timeLong % (60 * 60 * 24 * 30 * 12);

        if (year > 0 || minUnit >= 6) {
            diffDesc.append(year).append("年");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long month = timeLong / (60 * 60 * 24 * 30);
        timeLong = timeLong % (60 * 60 * 24 * 30);
        if (month > 0 || diffDesc.length() != 0 || minUnit >= 5) {
            if (month < 10) diffDesc.append("0");
            diffDesc.append(month).append("月");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long day = timeLong / 60 / 60 / 24;
        timeLong = timeLong % (60 * 60 * 24);
        if (day > 0 || diffDesc.length() != 0 || minUnit >= 4) {
            if (day < 10) diffDesc.append("0");
            diffDesc.append(day).append("天");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long hour = timeLong / (60 * 60);
        timeLong = timeLong % (60 * 60);
        if (hour > 0 || diffDesc.length() != 0 || minUnit >= 3) {
            if (hour < 10) diffDesc.append("0");
            diffDesc.append(hour).append("时");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long minitue = timeLong / 60;

        timeLong = timeLong % (60);
        if (minitue > 0 || diffDesc.length() != 0 || minUnit >= 2) {
            if (minitue < 10) diffDesc.append("0");
            diffDesc.append(minitue).append("分");
            if (!isFull) {
                return diffDesc.toString();
            }
        }

        Long second = timeLong;

        if (second < 10) diffDesc.append("0");
        diffDesc.append(second).append("秒");
        if (!isFull) {
            return diffDesc.toString();
        }
        return diffDesc.toString();
    }

    /**
     * 将指定的时间长度转换成常规描述性的时间字符串, 时长为24小时以内, 如 23:32:00。
     *
     * @param timeLong 指定的时间长度
     * @return 常规描述性的时间字符串
     */
    public static String formatTimeLongEn(long timeLong) {
        StringBuilder desc = new StringBuilder();
        //换算秒，防止Int溢出。
        timeLong = timeLong / 1000L;
        // 截成天以内
        timeLong = timeLong % (60 * 60 * 24);

        Long hour = timeLong / (60 * 60);
        timeLong = timeLong % (60 * 60);
        if (hour > 0) {
            if (hour < 10) desc.append("0");
            desc.append(hour).append(":");
        }

        Long minitue = timeLong / 60;
        timeLong = timeLong % (60);
        if (minitue < 10) {
            desc.append("0");
        }
        desc.append(minitue).append(":");

        Long second = timeLong;
        if (second < 10) desc.append("0");
        desc.append(second);
        return desc.toString();
    }
}
