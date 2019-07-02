package com.libs.utils.fileUtil;

import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.libs.k;

import java.io.File;


/**
 * @ author：mo
 * @ data：2019/2/1:14:55
 * @ 功能：FileProvider工具类
 */
public class FileProviderUtils {
    /**
     * Android N 以上获取文件 Uri (通过 FileProvider)
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile(File file) {
        return mFileProvider.getUriForFile(k.app(), getFileProviderAuthority(), file);
    }

    /**
     * 获取本应用 FileProvider 授权 包名+.fileprovider，在清单里声明的时候设置
     *
     * @return
     */
    public static String getFileProviderAuthority() {
        return k.app().getPackageName() + ".fileprovider";
    }

    public static class mFileProvider extends FileProvider {
    }
}
