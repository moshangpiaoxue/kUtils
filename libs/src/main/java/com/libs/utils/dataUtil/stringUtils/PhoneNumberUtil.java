package com.libs.utils.dataUtil.stringUtils;


import com.libs.modle.constants.RegexConstants;

/**
 * @ author：mo
 * @ data：2019/4/2:13:57
 * @ 功能：
 */
public class PhoneNumberUtil {
    /**
     * 是否手机号
     */
    public static boolean isPhone(String mobile_phone) {
        return RegexConstants.isRegex(mobile_phone, RegexConstants.RegexTel);
    }




}
