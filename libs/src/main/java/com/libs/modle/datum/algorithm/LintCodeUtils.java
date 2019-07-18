package com.libs.modle.datum.algorithm;

/**
 * @ author：mo
 * @ data：$data$:$time$
 * @ 功能：
 */
public class LintCodeUtils {
    /**
     * 37：反转一个3位整数
     *
     * @param number: A 3-digit number.
     * @return: Reversed number.
     */
    public static int reverseInteger37(int number) {
        //1、通过StringBuffer的反转方法
//        StringBuffer sb = new StringBuffer();
//        sb.append(number);
//        return Integer.parseInt(sb.reverse() + "");
        //2、通过余数拼
        return number % 10 * 10 * 10 + number / 10 % 10 * 10 + number / 10 / 10 % 10;
    }

    /**
     * 145. 大小写转换
     * 将一个字符由小写字母转换为大写字母
     *
     * @param character: a character
     * @return: a character
     */
    public static String lowercaseToUppercase(String character) {
        return character.toUpperCase();
    }
}
