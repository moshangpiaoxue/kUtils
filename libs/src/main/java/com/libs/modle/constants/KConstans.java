package com.libs.modle.constants;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ author：mo
 * @ data：2018/1/13：17:25
 * @ 功能：常量类
 */
public class KConstans {
    /**
     * 拍照标识
     */
    public static final int MEDIA_TAKE_PIC = 1;
    /**
     * 从相册选取图片标识
     */
    public static final int MEDIA_CHOOSE_PIC = 2;
    /**
     * 录像标识
     */
    public static final int MEDIA_TAKE_VIDEO = 3;
    /**
     * 从相册选取录像标识
     */
    public static final int MEDIA_CHOOSE_VIDEO = 4;
    /**
     * 录音标识
     */
    public static final int MEDIA_TAKE_SOUND = 5;




    /**
     * Byte 与 Byte 的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB 与 Byte 的倍数
     */
    public static final int KB = 1024;
    /**
     * MB 与 Byte 的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB 与 Byte 的倍数
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UnitBT {
    }
}
