package com.libs.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntRange;

import com.libs.k;
import com.libs.utils.logUtils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.libs.utils.image.BitmapUtil.getBitmapSize;
import static com.libs.utils.image.BitmapUtil.isEmptyBitmap;


/**
 * @ author：mo
 * @ data：2018/11/23：16:13
 * @ 功能：图片压缩--质量压缩。尺寸大小不变
 * <p>
 * 图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，
 * 来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法。那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的。
 * 如果是bit.compress(CompressFormat.PNG, quality, baos);这样的png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩。
 */
public class CompressIQuality {
    /**
     * 按质量压缩--质量
     *
     * @param src     源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality) {
        return compressByQuality(src, quality, false);
    }

    /**
     * 按质量压缩--质量
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality,
                                           final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) {src.recycle();}
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按质量压缩--最大值字节数
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize) {
        return compressByQuality(src, maxByteSize, false);
    }

    /**
     * 按质量压缩--最大值字节数
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src,
                                           final long maxByteSize,
                                           final boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0){ return null;}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {// 最好质量的不大于最大字节，则返回最佳质量
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节，则返回最差质量
                bytes = baos.toByteArray();
            } else {
                // 二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled()) {src.recycle();}
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 质量压缩方法
     *
     * @param imagePath 原图片路径
     * @param size      压缩图片到多少KB以内(单位KB)
     * @return 返回压缩后图片文件
     */
    public static File compressImageToSD(String imagePath, long size) {
        return compressImageToSD(BitmapFactory.decodeFile(imagePath), size);
    }

    /**
     * 质量压缩方法
     *
     * @param bitmap 原图片Bitmap
     * @param size   压缩图片到多少KB以内(单位KB)
     * @return 返回压缩后图片文件
     */
    public static File compressImageToSD(Bitmap bitmap, long size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到字节流中
        LogUtil.i("初始大小===" + getBitmapSize(bitmap));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        long fileSize = baos.toByteArray().length / 1024;
        LogUtil.i("============================图片压缩前大小=======" + fileSize + "====================================");
        int options = 90;
        if (fileSize / size > 5) {
            options = 10;
        } else if (fileSize / size > 4) {
            options = 20;
        } else if (fileSize / size > 3) {
            options = 30;
        } else if (fileSize / size > 2) {
            options = 50;
        }
// 循环判断如果压缩后图片是否大于1024kb,大于继续压缩
        while (fileSize > size && options > 0) {
            LogUtil.i("============================图片压缩后还是大于规定大小，重新压缩=================");
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到字节流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            LogUtil.i("===========================压缩到原来================" + options + "%");
            // 每次都减少10
            options -= 10;
            fileSize = baos.toByteArray().length / 1024;
        }
        LogUtil.i("============================图片压缩结束===" + fileSize + "========================================");
        // 把压缩后的字节存放到ByteArrayInputStream中
        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        File appDir = new File(k.app().getExternalFilesDir(null), "temp");
        if (!appDir.exists() && appDir.mkdir()) {
            LogUtil.i(appDir.getName() + "目录创建成功");
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            LogUtil.i("============================压缩后的图片保存到SD===========================================");
            FileOutputStream fos = new FileOutputStream(file);
            int temp = isBm.available();
            int bytesRead;
            byte[] buffer = new byte[isBm.available()];
            while ((bytesRead = isBm.read(buffer, 0, temp)) != -1) {
//                LogUtil.i("=======================================================================" + bytesRead);
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            fos.close();
            isBm.close();
            LogUtil.i("============================保存到SD成功===========================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
