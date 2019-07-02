package com.libs.utils.fileUtil;


import com.libs.modle.constants.Limits;

/**
 * @ author：mo
 * @ data：2019/2/13:16:09
 * @ 功能：文件尺寸
 */
public class FileSize {

    public static final int B = 0;
    public static final int KB = 1;
    public static final int MB = 2;
    public static final int GB = 3;

    public static float format(float size, @Limits.FileSizeDef int srcUnit, @Limits.FileSizeDef int destUnit) {
        return (float) (size * Math.pow(1024L, (srcUnit - destUnit)));
    }

    public static float formatByte(long size, @Limits.FileSizeDef int unit) {
        return format(size, B, unit);
    }
}
