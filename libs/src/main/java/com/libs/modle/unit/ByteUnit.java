package com.libs.modle.unit;

/**
 * @ author：mo
 * @ data：2019/8/29:17:49
 * @ 功能：
 */
public class ByteUnit {
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }
}
