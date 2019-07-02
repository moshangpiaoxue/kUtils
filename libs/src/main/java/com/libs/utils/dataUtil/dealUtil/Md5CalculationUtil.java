package com.libs.utils.dataUtil.dealUtil;

import java.security.MessageDigest;

/**
 * @ author：mo
 * @ data：2018/1/29：16:06
 * @ 功能：md5计算工具类
 */
public class Md5CalculationUtil {

    /**
     * md532位加密
     * @param string
     * @return
     */
    public static String encryptString(String string){
        char  hexDigits[] = {  '0' ,  '1' ,  '2' ,  '3' ,  '4' ,  '5' ,  '6' ,  '7' ,  '8' ,  '9' ,
                'a' ,  'b' ,  'c' ,  'd' ,  'e' ,  'f'  };
        try  {
            byte [] strTemp = string.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5" );
            mdTemp.update(strTemp);
            byte [] md = mdTemp.digest();
            int  j = md.length;
            char  str[] =  new   char [j *  2 ];
            int  k =  0 ;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return   new String(str);
        } catch  (Exception e) {
            return   null ;
        }
    }
}
