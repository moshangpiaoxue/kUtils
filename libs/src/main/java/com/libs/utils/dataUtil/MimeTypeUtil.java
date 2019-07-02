package com.libs.utils.dataUtil;

import android.webkit.MimeTypeMap;

/**
 * @ author：mo
 * @ data：2019/2/13:15:13
 * @ 功能：MimeType 相关
 */
public class MimeTypeUtil {
    private MimeTypeUtil() {
    }

    /**
     * 通过文件扩展名获取 Mime 类型
     *
     * @param extension 文件扩展名
     * @return Mime 类型, 无该 Mime 类型返回 null
     */
    public static String getMimeTypeFromExtension(String extension) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(extension)) {
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        }
        return null;
    }
}
