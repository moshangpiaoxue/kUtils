package com.libs.utils.dataUtil;

import java.util.Random;

/**
 * @ author：mo
 * @ data：2019/2/13:15:42
 * @ 功能：随机数相关
 */
public class RandomUtil {

    private RandomUtil() {
    }

    /**
     * 获取 boolean 随机数
     *
     * @return boolean 随机数
     */
    public static boolean getBoolean() {
        return new Random().nextBoolean();
    }

    /**
     * 获取 0 到 max 的随机数 (包含 max)
     *
     * @return int 随机数
     */
    public static int getInt(int max) {
        return new Random().nextInt(max + 1);
    }

    /**
     * 获取 0 到 max 的随机数 (不包含 max)
     *
     * @return int 随机数
     */
    public static int nextInt(int max) {
        return new Random().nextInt(max + 1);
    }

    /**
     * 获取 min 到 max 的随机数 (包含 max)
     *
     * @return int 随机数
     */
    public static int getInt(int min, int max) {
        int diff = max - min;
        if (diff < 0) {
            throw new IllegalArgumentException("max should bigger than min");
        } else if (diff == 0) {
            return min;
        }
        return min + new Random().nextInt(diff + 1);
    }

    /**
     * 获取指定长度的随机数字字符串
     *
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String getNumber(int length) {
        String letters = "0123456789";
        return getRandom(letters, length);
    }

    /**
     * 获取指定长度的随机小写字母字符串
     *
     * @param length 字符串长度
     * @return 随机小写字母字字符串
     */
    public static String getLowerCaseLetter(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        return getRandom(letters, length);
    }

    /**
     * 获取指定长度的随机大写字母字符串
     *
     * @param length 字符串长度
     * @return 随机大写字母字字符串
     */
    public static String getUpperCaseLetterString(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getRandom(letters, length);
    }

    /**
     * 获取指定长度的随机大小写字母字符串
     *
     * @param length 字符串长度
     * @return 随机大小写字母字字符串
     */
    public static String getLetter(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getRandom(letters, length);
    }

    /**
     * 获取指定长度的随机字母 + 大小写字母字符串
     *
     * @param length 字符串长度
     * @return 随机字母+大小写字母字符串
     */
    public static String getNumberAndLetter(int length) {
        String letters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getRandom(letters, length);
    }

    /**
     * 在指定的字符串所限定的字符中, 获取指定长度的随机字符串
     *
     * @param source 字符源
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandom(String source, int length) {
        return getRandom(source.toCharArray(), length);
    }

    /**
     * 在指定的字符数组所限定的字符中, 获取指定长度的随机字符串
     *
     * @param source 字符源
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandom(char[] source, int length) {
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(source[random.nextInt(source.length)]);
        }
        return str.toString();
    }
}
