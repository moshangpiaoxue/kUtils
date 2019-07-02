package com.libs.modle.datum.algorithm.calculation;

/**
 * @author：mo
 * @data：2018/3/1 0001
 * @功能：递归算法
 */

public class Recursion {
    /**
     * 递归算法实现
     */
    public static int foo(int i) {
        if (i <= 0) {
            return 0;
        } else if (i > 0 && i <= 2) {
            return 1;
        }
        return foo(i - 1) + foo(i - 2);
    }
}
