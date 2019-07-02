package com.libs.utils.dataUtil;


/**
 * @ author：mo
 * @ data：2017/11/20 0020
 * @ 功能：LongUtil
 */

public class LongUtil {
    /**
     * 类型转换：string--》》long
     *
     * @param str
     * @return
     */
    public static Long getLong(String str) {
        return StringUtil.isEmpty(str) ? 0 : Long.parseLong(str);
    }

    public static int getLongSize(long lon){
        return  (lon + "").length();
    }
}
