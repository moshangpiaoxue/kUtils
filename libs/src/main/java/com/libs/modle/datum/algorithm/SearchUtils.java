package com.libs.modle.datum.algorithm;

import java.util.List;

/**
 * @ author：mo
 * @ data：$data$:$time$
 * @ 功能：
 */
public class SearchUtils {
    /**
     * 二分查找-递归方式
     *
     * @param list  数据（有序且升序）
     * @param start 起点
     * @param end   终点
     * @param num   查找的值
     * @return 查找的值所处位置
     */
    public static int searchBinary(List<Integer> list, int start, int end, int num) {
        if (list == null || list.size() == 0) {
            return -1;
        }
        if (start == end) {
            if (list.get(start) != num) {
                return -1;
            }
        }
        if (end > list.size() - 1) {
            end = list.size() - 1;
        }
        if (list.get(start) > num && list.get(end) > num || list.get(start) < num && list.get(end) < num) {
            return -1;
        }
        int temp = start + (end - start) / 2;
        if (list.get(temp) > num) {
            end = temp - 1;
            return searchBinary(list, start, end, num);
        } else if (list.get(temp) < num) {
            start = temp + 1;
            return searchBinary(list, start, end, num);
        } else {
            return temp;
        }
    }

    /**
     * 二分查找-while方式
     *
     * @param list 数据（有序且升序）
     * @param num  查找的值
     * @return 位置
     */
    public static int searchBinary(List<Integer> list, int num) {
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int temp = (start + end) / 2;
            if (list.get(temp) < num) {
                start = temp + 1;
            } else if (list.get(temp) > num) {
                end = temp - 1;
            } else {
                return temp;
            }
        }
        return -1;
    }
    /**
     * 二分查找-while方式-查找某值在数据里第一次出现的位置
     *
     * @param list 数据（有序且升序）
     * @param num  查找的值
     * @return 位置
     */
    public static int searchBinaryFirstEqual(List<Integer> list, int num) {
        int start = 0;
        int end = list.size() - 1;
        while (start <= end) {
            int temp = (start + end) / 2;
            if (list.get(temp) < num) {
                start = temp + 1;
            } else if (list.get(temp) >= num) {
                end = temp - 1;
            }
            if (start < list.size() && list.get(temp) == num) {
                return temp;
            }
        }
        return -1;
    }

}
