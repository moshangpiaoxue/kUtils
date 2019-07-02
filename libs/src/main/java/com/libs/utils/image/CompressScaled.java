package com.libs.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.ScreenUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;



/**
 * @ author：mo
 * @ data：2018/7/3 0003 11:35
 * @ 功能：图片压缩-等比压缩
 */
public class CompressScaled {

    /**
     * 根据maxWidth, maxHeight计算最合适的inSampleSize
     * @param options
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static int getSampleSize(BitmapFactory.Options options,int maxWidth, int maxHeight) {
        // raw height and width of image
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;

        // calculate best sample size
        int inSampleSize = 0;
        if (rawHeight > maxHeight || rawWidth > maxWidth) {
            float ratioWidth = (float) rawWidth / maxWidth;
            float ratioHeight = (float) rawHeight / maxHeight;
            inSampleSize = (int) Math.min(ratioHeight, ratioWidth);
        }
        inSampleSize = Math.max(1, inSampleSize);

        return inSampleSize;
    }
    /**
     * 根据宽高等比压缩图片
     *
     * @param imagePath 原图路径
     * @param dstWidth  压缩后的宽度
     * @param dstHeight 压缩后的高度
     * @return 压缩后的bitmap
     */
    public static Bitmap getScaledBitmap(String imagePath, int dstWidth, int dstHeight) {
        return getBitmapScaled(BitmapFactory.decodeFile(imagePath), dstWidth, dstHeight);
    }

    public static Bitmap getScaledBitmap(String imagePath) {
        return getBitmapScaled(BitmapFactory.decodeFile(imagePath), ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight());
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap) {
        return getBitmapScaled(bitmap, ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight());
    }
    /**
     * 图片按比例大小压缩方法
     * @param image
     * @return
     */
    public static Bitmap getBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 192f;//这里设置高度为800f
        float ww = 374f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0){
            be = 1;}
        newOpts.inSampleSize = be;//设置缩放比例
        //newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        // return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        return bitmap;
    }
    /**
     * 根据宽高等比压缩图片
     * 策略：
     * 1、当原宽高均小于预设宽高时，不作处理
     * 2、宽高比不同时，说明如果根据设定的数据处理，图像会变形，处理
     * 3、其他情况，根据比例缩小，不用考虑放大的情况，1已经处理
     * 4、超长或超宽图没有考虑
     *
     * @param bitmap    原图
     * @param newWidth  目标宽度
     * @param newHeight 目标高度
     * @return
     */
    public static Bitmap getBitmapScaled(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();
        LogUtil.i("原宽==" + oldWidth + "\n原高==" + oldHeight);
        LogUtil.i("欲设宽==" + newWidth + "\n欲设高==" + newHeight);
        //1；原宽高均小于预设宽高
        if (oldWidth < newWidth && oldHeight < newHeight) {
            LogUtil.i("bitmap宽高均小于设定宽高。现宽高为：" + oldWidth + ":" + oldHeight + "欲设宽高为：" + newWidth + ":" + newHeight);
            return bitmap;
        }
        // 计算缩放比例
        float scaleWidth = 0;
        float scaleHeight = 0;
        float oldScale = oldWidth / oldHeight;
        float newScale = newWidth / newHeight;
        LogUtil.i("oldScale==" + oldScale);
        LogUtil.i("newScale==" + newScale);
        //2、形状方向
        if (oldWidth / oldHeight != newWidth / newHeight) {
            scaleWidth = ((float) newHeight) / oldWidth;
            scaleHeight = ((float) newWidth) / oldHeight;
            LogUtil.i("形状相反");
        } else {
            scaleWidth = ((float) newWidth) / oldWidth;
            scaleHeight = ((float) newHeight) / oldHeight;
            LogUtil.i("形状相同");
        }
        LogUtil.i("缩放比例宽==" + scaleWidth + "\n缩放比例高==" + scaleHeight);
        // 得到新的图片
        Bitmap newbm = BitmapUtil.getBitmapScale(bitmap, scaleWidth, scaleHeight);
        return newbm;
    }

    /**
     * 保持长宽比缩小Bitmap 超大图截取处理
     *
     * @param bitmap
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap getBitmapLongScaled(Bitmap bitmap, int maxWidth, int maxHeight) {
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();
        LogUtil.i("原宽==" + originWidth + "\n原高==" + originHeight);
        LogUtil.i("欲设宽==" + maxWidth + "\n欲设高==" + maxHeight);
        if (originWidth < maxWidth && originHeight < maxHeight) {
            LogUtil.i("bitmap宽高均小于设定宽高。现宽高为：" + originWidth + ":" + originHeight + "欲设宽高为：" + maxWidth + ":" + maxHeight);
            return bitmap;
        }
        int width = originWidth;
        int height = originHeight;
        // 若图片过宽, 则保持长宽比缩放图片
        if (originWidth > maxWidth) {
            width = maxWidth;
            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);
            bitmap = getBitmapScaled(bitmap, width, height);
        }
        // 若图片过长, 则从上端截取
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }
        return bitmap;
    }
}
