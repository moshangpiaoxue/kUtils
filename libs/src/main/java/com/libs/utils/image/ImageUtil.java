package com.libs.utils.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.libs.utils.fileUtil.CloseUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.libs.utils.image.BitmapUtil.isEmptyBitmap;

/**
 * @ author：mo
 * @ data：2016/08/12
 * @ 功能：图片相关工具类
 */
public final class ImageUtil {

    private ImageUtil() {
        throw new UnsupportedOperationException("这不是bug...");
    }

    /**
     * 是否是图片
     * @param file
     * @return
     */
    public static boolean isImage(final File file) {
        return file != null && isImage(file.getPath());
    }

    /**
     * 是否是图片
     * @param filePath
     * @return
     */
    public static boolean isImage(final String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".BMP") || path.endsWith(".GIF");
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    public static String getImageType(final String filePath) {
        return getImageType(getFileByPath(filePath));
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    public static String getImageType(final File file) {
        if (file == null) {return null;}
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(is);
        }
    }


    /**
     * 流获取图片类型
     *
     * @param is 图片输入流
     * @return 图片类型
     */
    public static String getImageType(final InputStream is) {
        if (is == null){ return null;}
        try {
            byte[] bytes = new byte[8];
            return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取图片类型
     *
     * @param bytes bitmap 的前 8 字节
     * @return 图片类型
     */
    public static String getImageType(final byte[] bytes) {
        if (isJPEG(bytes)) {return "JPEG";}
        if (isGIF(bytes)) {return "GIF";}
        if (isPNG(bytes)){ return "PNG";}
        if (isBMP(bytes)) {return "BMP";}
        return null;
    }

    private static boolean isJPEG(final byte[] b) {
        return b.length >= 2
                && (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
    }

    private static boolean isGIF(final byte[] b) {
        return b.length >= 6
                && b[0] == 'G' && b[1] == 'I'
                && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }
    /**
     * 判断是否为gif
     */
    public static boolean isGIF(String filename) {
        String end = null;
        String[] strings = filename.split("!");
        filename = strings[0];
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                end = filename.substring(dot, filename.length());
            }
        }
        if (end == null) {
            return false;
        }
        return (".gif".compareTo(end) == 0) || (".GIF".compareTo(end) == 0);
    }
    private static boolean isPNG(final byte[] b) {
        return b.length >= 8
                && (b[0] == (byte) 137 && b[1] == (byte) 80
                && b[2] == (byte) 78 && b[3] == (byte) 71
                && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(final byte[] b) {
        return b.length >= 2
                && (b[0] == 0x42) && (b[1] == 0x4d);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final CompressFormat format) {
        return save(src, getFileByPath(filePath), format, false);
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final CompressFormat format,
                               final boolean recycle) {
        if (isEmptyBitmap(src) || !createFileByDeleteOldFile(file)) {return false;}
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled()){ src.recycle();}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(os);
        }
        return ret;
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }
    private static boolean isSpace(final String s) {
        if (s == null){ return true;}
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) {return false;}
        if (file.exists() && !file.delete()){ return false;}
        if (!createOrExistsDir(file.getParentFile())){ return false;}
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src, final File file, final CompressFormat format) {
        return save(src, file, format, false);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final CompressFormat format,
                               final boolean recycle) {
        return save(src, getFileByPath(filePath), format, recycle);
    }




}
