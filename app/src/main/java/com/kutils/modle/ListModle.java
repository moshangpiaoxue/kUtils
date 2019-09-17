package com.kutils.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/9/9:9:26
 * @ 功能：
 */
public class ListModle {

    public static List<String> getList(int num) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < num; i++) {
            list.add(i + "");
        }
        return list;
    }
}
