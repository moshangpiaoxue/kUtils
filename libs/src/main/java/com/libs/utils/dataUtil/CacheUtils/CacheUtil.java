package com.libs.utils.dataUtil.CacheUtils;

import com.libs.utils.fileUtil.FileUtil;

import java.io.File;



/**
 * @ author：mo
 * @ data：2017/12/13 0013
 * @ 功能： 缓存工具类
 */
public class CacheUtil {
    /**
     * 获取缓存大小
     */
    public static String getCacheSize() {

        String cacheSize = null;
        try {
            File cacheDir = new File(FileUtil.getExternalCacheDir());
            cacheSize = DataCleanManager.getCacheSize(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheSize == null ? "" : cacheSize;
    }

    /**
     * 清除缓存
     */
    public  static void cleanCache() {
        DataCleanManager.deleteFolderFile(FileUtil.getExternalCacheDir(), false);
    }

}
