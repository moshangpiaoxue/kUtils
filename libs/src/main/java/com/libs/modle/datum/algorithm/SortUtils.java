package com.libs.modle.datum.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ author：mo
 * @ data：$data$:$time$
 * @ 功能：
 */
public class SortUtils {

    public static List<Integer> getList(int[] list) {
        List<Integer> list1 = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            list1.add(list[i]);
        }
        return list1;
    }

    public static List<Integer> SortBubble(int[] list, Boolean isUp) {
        int temp = 0;
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = 0; j < list.length - 1 - i; j++) {
                if (isUp) {
                    if (list[j] > list[j + 1]) {
                        temp = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = temp;
                    }
                } else {
                    if (list[j] < list[j + 1]) {
                        temp = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = temp;
                    }

                }

            }
        }
        return getList(list);

    }

    public static List<Integer> Sort(List<Integer> list, Boolean isUp) {
        Collections.sort(list);
        if (!isUp) {
            Collections.reverse(list);
        }
        return list;
    }

    public static List<Integer> Sort(int[] list, Boolean isUp) {
        List<Integer> list1 = getList(list);
        return Sort(list1, isUp);
    }
}
