package com.libs.utils.fileUtil;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @ author：mo
 * @ data：2018/12/26
 * @ 功能：文件路径工具类
 */
public class FileDirUtil {
    /**
     * 获取相册文件
     *
     * @param albumName 子文件名"draw"、
     * @return
     */
    public static File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStorageDirectory(), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }
}
