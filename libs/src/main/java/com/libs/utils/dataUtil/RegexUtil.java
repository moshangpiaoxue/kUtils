package com.libs.utils.dataUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ author：mo
 * @ data：2018/10/10
 * @ 功能：
 */
public class RegexUtil {
    /**
     * 座机号正则
     * https://blog.csdn.net/wmx690/article/details/80354321?tdsourcetag=s_pcqq_aiomsg
     * ^[0-9]{3,4}[0-9]{7,8}$
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
//    public static String RegexZuoJi = "^0\\d{2,3}\\[0-9]{3,4}[0-9]{7,8}$";
    public static String RegexZuoJi = "^[0-9]{3,4}[0-9]{7,8}$";
    /**
     * 手机号正则
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
    public static String RegexTel = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * 密码正则
     * 6-16位，由数字和字母组合
     */
    public static String RegexPassWord = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    /**
     * 邮箱正则
     */
    public static String RegexEmail = "(^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$)";
    /**
     * 银行卡号正则
     */
    public static String RegexBankNumber = "(^[0-9]{9,25}$)";
    /**
     * 检测中文正则
     */
    public static String RegexChanese = "[\u4e00-\u9fa5]";

    /**
     * 正则验证
     *
     * @param regex 正则表达式
     * @param str   待验证字符串
     * @return
     */
    public static Boolean regex(String regex, String str) {
        if (StringUtil.isEmpty(regex) || StringUtil.isEmpty(str)) {
            return false;
        }
        Pattern p2 = Pattern.compile(regex);
        Matcher m = p2.matcher(str);
        return m.matches();
    }

    public static Boolean regex2(String regex, String str) {
        if (StringUtil.isEmpty(regex) || StringUtil.isEmpty(str)) {
            return false;
        }
        return Pattern.matches(regex, str);
    }

    /**
     * 判断是否是座机号
     *
     * @param num
     * @return
     */
    public static Boolean isZuoJiNum(String num) {
        return regex(RegexZuoJi, num);
    }

    /**
     * 判断是否是手机号
     *
     * @param num
     * @return
     */
    public static Boolean isTelNum(String num) {
        return regex(RegexTel, num);
    }

    /**
     * 判断是否是密码
     *
     * @param str
     * @return
     */
    public static Boolean isPassWord(String str) {
        return regex(RegexPassWord, str);
    }

    /**
     * 判断是否是邮箱
     *
     * @param str
     * @return
     */
    public static Boolean isEmail(String str) {
        return regex(RegexEmail, str);
    }

    /**
     * 判断是否是银行卡号
     *
     * @param str
     * @return
     */
    public static Boolean isBankNumber(String str) {
        return regex(RegexBankNumber, str);
    }

    /**
     * 判断是否是车牌号包括新能源
     */
    public static boolean isNewCarNumber(String carNumber) {
        Pattern p = Pattern.compile("^(([\\u4e00-\\u9fa5]{1}[A-Z]{1})[-]?|([wW][Jj][\\u4e00-\\u9fa5]{1}[-]?)" +
                "|([a-zA-Z]{2}))([A-Za-z0-9]{5}|[DdFf][A-HJ-NP-Za-hj-np-z0-9][0-9]{4}|[0-9]{5}[DdFf])" +
                "|^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领A-Z]" +
                "{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳]{1}$");
        if (!carNumber.isEmpty()) {
            Matcher m = p.matcher(carNumber.trim());
            if (m != null) {
                if (m.matches()) {
                    String substring = carNumber.substring(1);
                    if (substring.contains("O") || substring.contains("o") || substring.contains("I") || substring.contains("i")) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 匹配数字
     *
     * @param str
     * @return
     */
    public static boolean isNumericzidai(String str) {
        if (str.length() == 11) {
            Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
