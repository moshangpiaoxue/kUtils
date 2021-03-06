package com.libs.utils.dataUtil;

import java.util.Collection;

/**
 * @ author：mo
 * @ data：2018/12/13：16:36
 * @ 功能：集合操作工具类
 */
public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }
}
