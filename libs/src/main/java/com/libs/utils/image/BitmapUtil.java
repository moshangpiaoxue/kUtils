package com.libs.utils.image;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.view.View;

import com.libs.k;
import com.libs.utils.ResUtil;
import com.libs.utils.dataUtil.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * @ author：mo
 * @ data：2017/12/7：16:19
 * @ 功能：bitmap工具类
 */
public class BitmapUtil {

    public enum Orientation {
        //上
        TOP,
        //下
        BOTTOM,
        //左
        LEFT,
        //右
        RIGHT,
        //左上
        LEFT_TOP,
        //左下
        LEFT_BOTTOM,
        //右上
        RIGHT_TOP,
        //右下
        RIGHT_BOTTOM
    }

    /**
     * 判断 bitmap 对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 获取bitmap大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        //SInce API 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        //Since API 12
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 图像的放大缩小--根据宽高比例
     *
     * @param bitmap 源位图对象
     * @param scaleX 宽度比例系数
     * @param scaleY 高度比例系数
     * @return 返回位图对象
     */
    public static Bitmap getBitmapScale(Bitmap bitmap, float scaleX, float scaleY) {
        return getBitmapScale(bitmap, scaleX, scaleY, false);
    }

    /**
     * 图像放大缩小--根据宽度和高度
     *
     * @param src
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapScale(Bitmap src, int width, int height) {
        return getBitmapScale(src, width, height, false);
    }

    /**
     * 图像的放大缩小--根据宽高比例
     *
     * @param bitmap      源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    public static Bitmap getBitmapScale(final Bitmap bitmap, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (recycle) {
            recyleBitmap(ret);
        }
        return ret;
    }

    /**
     * 图像放大缩小--根据宽度和高度
     *
     * @param bitmap    源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return
     */
    public static Bitmap getBitmapScale(final Bitmap bitmap, final int newWidth, final int newHeight, final boolean recycle) {
        if (isEmptyBitmap(bitmap)) {
            return null;
        }
        Bitmap ret = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        if (recycle) {
            recyleBitmap(ret);
        }
        return ret;
    }


    /**
     * 获取图片的类型
     *
     * @param path
     * @return
     */
    public static String getBitmapType(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outMimeType;
    }

    /**
     * 获取图片的形状
     *
     * @param bitmap
     * @return 1 长图 2宽图 3正方形
     */
    public static int getBitmapShape(Bitmap bitmap) {
        int imgType = 0;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        if (bitmapWidth == bitmapHeight) {
            imgType = 3;
        } else if (bitmapWidth > bitmapHeight) {
            imgType = 2;
        } else if (bitmapWidth < bitmapHeight) {
            imgType = 1;
        }
        return imgType;
    }

    /**
     * 获取图像文件的信息，是否旋转了90度，如果是则反转
     *
     * @param bitmap
     * @param path   图像的路径
     * @return
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
        int degree = 0;
        String mimeType = getBitmapType(path);
        if (!TextUtils.isEmpty(mimeType) && !mimeType.equals("image/png")) {
            degree = getRotateDegree(path);
        }
        if (degree != 0) {
            try {
                Matrix m = new Matrix();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                m.setRotate(degree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error err) {
                err.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 读取图像属性：旋转的角度
     *
     * @param path 图像绝对路径
     * @return degree旋转的角度
     */
    public static int getRotateDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图像的旋转角度置为0
     *
     * @param path
     */
    private void setPictureDegreeZero(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            //修正图像的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            //例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 图像质量压缩
     *
     * @param image
     * @param srcPath 要保存的路径
     * @return
     */
    public static Bitmap compressImage(Bitmap image, String srcPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图像是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图像
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try {
            FileOutputStream out = new FileOutputStream(srcPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 读取资源文件中图片
     *
     * @param resId 资源 Id
     * @return Bitmap 对象
     */
    public static Bitmap getBitmap(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(k.app().getResources(), resId);
//        return ResUtil.getBitmap(resId);
    }

    /**
     * File 转 Bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(final File file) {
        return file == null ? null : BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * Uri 转 Bitmap
     *
     * @param imageUri
     * @return
     */
    public static Bitmap getBitmap(Uri imageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(k.app().getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * InputStream 转 Bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is) {
        return is == null ? null : BitmapFactory.decodeStream(is);
    }

    /**
     * url 转 Bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url) {
        URL u = null;
        Bitmap bmp = null;
        try {
            u = new URL(url);

        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream ins = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(ins);
            ins.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bmp;
    }

    /**
     * filePath 转 Bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap2(final String filePath) {
        return StringUtil.isSpace(filePath) ? null : BitmapFactory.decodeFile(filePath);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filePath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(fis);
    }


    /**
     *FileDescriptor 转 Bitmap
     * @param fd 文件描述
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd) {
        return fd == null ? null : BitmapFactory.decodeFileDescriptor(fd);
    }

    /**
     * 获取Asset/ 中的图片
     *
     * @param fileName
     * @return
     */
    private Bitmap getBitmapAssets(String fileName) {
        Bitmap image = null;
        AssetManager am = ResUtil.getResource().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     *Drawable 转 Bitmap
     * @param drawable
     * @return
     */

    public static Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * view 转 bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap getBitmap(final View view) {
        if (view == null) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    public static Bitmap getBitmap2(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    /**
     * bitmap转为drawable
     *
     * @param bitmap 转为drawable 的bitmap
     * @return 返回drawable
     */
    public static Drawable toDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(k.app().getResources(), bitmap);
    }

    /**
     * byte[]转Bitmap
     *
     * @param bytes
     * @return
     */
    public static Bitmap getBitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Bitmap转byte[]
     *
     * @param bitmap
     * @param format CompressFormat.PNG/Bitmap.CompressFormat.JPEG
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 如果bitmap没有被回收，则强制回收bitmap
     *
     * @param bitmap
     */
    public static void recyleBitmap(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    /**
     * 获取圆形图像
     *
     * @param bitmap 传入Bitmap对象
     * @return 返回圆形的bitmap
     */
    public static Bitmap getBitmapRound(Bitmap bitmap) {
        return getBitmapRound(bitmap, 0, 0xff424242, false);
    }

    /**
     * 获取圆形图像
     *
     * @param src         源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @param recycle     是否回收
     * @return
     */
    public static Bitmap getBitmapRound(final Bitmap src, @IntRange(from = 0) int borderSize, @ColorInt int borderColor, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;
        RectF rectF = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        matrix.preScale((float) size / width, (float) size / height);
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        canvas.drawRoundRect(rectF, center, center, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        if (recycle) {
            recyleBitmap(src);
        }
        return ret;
    }


    /**
     * 获取浮雕效果的图像
     *
     * @param bitmap 图像
     * @return bmp
     */
    public static Bitmap getBitmapRelief(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);
        int r, g, b, a;
        int r1, g1, b1, a1;
        int color, colorBefore;
        int[] newPx = new int[width * height];
        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);
            a = Color.alpha(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);
            a1 = Color.alpha(color);

            r = r - r1 + 127;
            g = g - g1 + 127;
            b = b - b1 + 127;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }

            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 怀旧特效
     *
     * @param bmp 原图片
     * @return
     */
    public static Bitmap getBitmapOld(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
                int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255
                        : newB);
                pixels[width * i + k] = newColor;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 获取底片效果的图像
     *
     * @param bitmap 图像
     * @return bmp
     */
    public static Bitmap getBitmapNegative(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);
        int r, g, b, a;
        int color;
        int[] newPx = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }

            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 获取带圆角的图像
     *
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap getBitmapRadius(Bitmap bitmap, int radius) {
        return getBitmapRadius(bitmap, radius, 0, 0xff424242, false);
    }

    /**
     * 转为圆角图片
     *
     * @param src         源图片
     * @param radius      圆角的度数
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @param recycle     是否回收
     * @return 圆角图片
     */
    public static Bitmap getBitmapRadius(final Bitmap src, final float radius, @IntRange(from = 0) int borderSize, @ColorInt int borderColor, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        RectF rectF = new RectF(0, 0, width, height);
        float halfBorderSize = borderSize / 2f;
        rectF.inset(halfBorderSize, halfBorderSize);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 转为 alpha 位图
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return alpha 位图
     */
    public static Bitmap getBitmapAlpha(final Bitmap src, final Boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.extractAlpha();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 添加边框
     *
     * @param src          源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param isCircle     是否画圆
     * @param cornerRadius 圆角半径
     * @param recycle      是否回收
     * @return 边框图
     */
    public static Bitmap getBitmapBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color, final boolean isCircle, final float cornerRadius, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        int width = ret.getWidth();
        int height = ret.getHeight();
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderSize);
        if (isCircle) {
            float radius = Math.min(width, height) / 2f - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        } else {
            int halfBorderSize = borderSize >> 1;
            RectF rectF = new RectF(halfBorderSize, halfBorderSize,
                    width - halfBorderSize, height - halfBorderSize);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        }
        return ret;
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标 x
     * @param y        起始坐标 y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    public static Bitmap getBitmapTextWatermark(final Bitmap src, final String content, final float textSize, @ColorInt final int color, final float x, final float y, final boolean recycle) {
        if (isEmptyBitmap(src) || content == null) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }


    /**
     * 添加图片水印(叠加)
     *
     * @param src     源图片
     * @param tip     图片水印
     * @param x       起始坐标 x
     * @param y       起始坐标 y
     * @param alpha   透明度
     * @param recycle 是否回收
     * @return 带有图片水印的图片
     */
    public static Bitmap getBitmapImageWatermark(final Bitmap src, final Bitmap tip, final int x, final int y, @IntRange(from = 0, to = 255) final int alpha, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        if (!isEmptyBitmap(tip)) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(tip, x, y, paint);
        }
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 盖印颜色蒙层
     *
     * @param src   源 Bitmap 对象
     * @param color 盖印颜色
     * @return 盖印后的图片
     */
    public static Bitmap getBitmapColorMask(Bitmap src, int color) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = src.copy(src.getConfig(), true);
        Canvas canvas = new Canvas(ret);
        canvas.drawColor(color);
        return ret;
    }

    /**
     * 获取拼接图像
     *
     * @param firstBitmap
     * @param secondBitmap
     * @param orientation  拼接方向
     * @return
     */
    public static Bitmap getBitmapSplicing(Bitmap firstBitmap, Bitmap secondBitmap, Orientation orientation) {
        if (firstBitmap == null) {
            return null;
        }
        if (secondBitmap == null) {
            return firstBitmap;
        }
        final int fw = firstBitmap.getWidth();
        final int fh = firstBitmap.getHeight();
        final int sw = secondBitmap.getWidth();
        final int sh = secondBitmap.getHeight();
        Bitmap bitmap = null;
        Canvas canvas = null;
        if (orientation == Orientation.TOP) {
            bitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(secondBitmap, 0, 0, null);
            canvas.drawBitmap(firstBitmap, 0, sh, null);
        } else if (orientation == Orientation.BOTTOM) {
            bitmap = Bitmap.createBitmap(fw > sw ? fw : sw, fh + sh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(firstBitmap, 0, 0, null);
            canvas.drawBitmap(secondBitmap, 0, fh, null);
        } else if (orientation == Orientation.LEFT) {
            bitmap = Bitmap.createBitmap(fw + sw, sh > fh ? sh : fh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(secondBitmap, 0, 0, null);
            canvas.drawBitmap(firstBitmap, sw, 0, null);
        } else if (orientation == Orientation.RIGHT) {
            bitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(firstBitmap, 0, 0, null);
            canvas.drawBitmap(secondBitmap, fw, 0, null);
        }
        return bitmap;
    }


    /**
     * 获取带倒影图像
     *
     * @param src              原图
     * @param reflectionHeight 倒影高度
     * @param spacing          间距
     * @param recycle          是否回收
     * @return
     */
    public static Bitmap getBitmapReflection(final Bitmap src, final int reflectionHeight, int spacing, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight - reflectionHeight, srcWidth, reflectionHeight, matrix, false);
        Bitmap ret = Bitmap.createBitmap(srcWidth, srcHeight + reflectionHeight, src.getConfig());
        Canvas canvas = new Canvas(ret);
        //  绘制原图像
        canvas.drawBitmap(src, 0, 0, null);
        // 绘制倒影图像
        canvas.drawBitmap(reflectionBitmap, 0, srcHeight + spacing, null);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 线性渲染-沿Y轴高到低渲染
        LinearGradient shader = new LinearGradient(
                0, srcHeight,
                0, ret.getHeight() + spacing,
                0x70FFFFFF,
                0x00FFFFFF,
                Shader.TileMode.MIRROR);
        paint.setShader(shader);
        // 取两层绘制交集，显示下层。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 绘制渲染倒影的矩形
        canvas.drawRect(0, srcHeight + spacing, srcWidth, ret.getHeight(), paint);
        if (!reflectionBitmap.isRecycled()) {
            reflectionBitmap.recycle();
        }
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 获取只有倒影的图像
     *
     * @param src
     * @return
     */
    public static Bitmap getBitmapReflectionSingle(Bitmap src) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        // 绘制高质量32位图
        Bitmap bitmap = Bitmap.createBitmap(w, h / 2, Bitmap.Config.ARGB_8888);
        // 创建沿X轴的倒影图像
        Matrix m = new Matrix();
        m.setScale(1, -1);
        Bitmap t_bitmap = Bitmap.createBitmap(src, 0, h / 2, w, h / 2, m, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        // 绘制倒影图像
        canvas.drawBitmap(t_bitmap, 0, 0, paint);
        // 线性渲染-沿Y轴高到低渲染
        Shader shader = new LinearGradient(0, 0, 0, h / 2, 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        // 取两层绘制交集。显示下层。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 绘制渲染倒影的矩形
        canvas.drawRect(0, 0, w, h / 2, paint);
        return bitmap;
    }


    /**
     * 获取置灰效果图像
     *
     * @param src
     * @return
     */
    public static Bitmap getBitmapGrey(final Bitmap src) {
        return getBitmap(src, 0, 0, 1f);
    }

    /**
     * 设置图像 色调，饱和度，亮度
     *
     * @param bitmap
     * @param rotate     色调(对比度,色相)；正负180（0=原图）
     * @param saturation 饱和度：0-2（0=置灰，0-1=减小饱和度，1=原图，大于1=增加饱和度，大于2也可以，但是基本没法看了）
     * @param scale      亮度；0-2（0=纯黑 1=原图 2=凑合能看，大于2也可以，但是基本没法看了）
     * @return
     */
    public static Bitmap getBitmap(Bitmap bitmap, float rotate, float saturation, float scale) {
        // 创建副本，用于将处理过的图片展示出来而不影响原图，Android系统也不允许直接修改原图
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 修改色调,即色彩矩阵围绕某种颜色分量旋转
        ColorMatrix rotateMatrix = new ColorMatrix();

        // 0,1,2分别代表像素点颜色矩阵中的Red，Green,Blue分量
        // 控制让红色区在色轮上旋转的角度
        rotateMatrix.setRotate(0, rotate);
        // 控制让绿红色区在色轮上旋转的角度
        rotateMatrix.setRotate(1, rotate);
        // 控制让蓝色区在色轮上旋转的角度
        rotateMatrix.setRotate(2, rotate);

        // 修改饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        // 修改亮度，即某种颜色分量的缩放
        ColorMatrix scaleMatrix = new ColorMatrix();
        // 分别代表三个颜色分量的亮度,红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化
        scaleMatrix.setScale(scale, scale, scale, 1);

        //将三种效果结合
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(rotateMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(scaleMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }


    /**
     * 裁剪图片
     *
     * @param src     源图片
     * @param x       开始坐标 x
     * @param y       开始坐标 y
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @param recycle 是否回收
     * @return 裁剪后的图片
     */
    public static Bitmap getBitmapClip(final Bitmap src, final int x, final int y, final int width, final int height, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子 x    正数=向左 负数=向右
     * @param ky      倾斜因子 y     正数=向下 负数=向上
     * @param px      平移因子 x
     * @param py      平移因子 y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    public static Bitmap getBitmapSkew(final Bitmap src, final float kx, final float ky, final float px, final float py, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * 偏移效果
     *
     * @param origin 原图
     * @return 偏移后的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标    没试出怎么用 直接给0
     * @param py      旋转点纵坐标    没试出怎么用 直接给0
     * @param recycle 是否回收
     * @return 旋转后的图片
     */
    public static Bitmap getBitmapRotate(final Bitmap src, final int degrees, final float px, final float py, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        if (degrees == 0) {
            return src;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return ret;
    }

    /**
     * LOMO特效
     *
     * @param bitmap 原图片
     * @return
     */
    public static Bitmap getBitmapLomo(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int dst[] = new int[width * height];
        bitmap.getPixels(dst, 0, width, 0, 0, width, height);

        int ratio = width > height ? height * 32768 / width : width * 32768 / height;
        int cx = width >> 1;
        int cy = height >> 1;
        int max = cx * cx + cy * cy;
        int min = (int) (max * (1 - 0.8f));
        int diff = max - min;

        int ri, gi, bi;
        int dx, dy, distSq, v;

        int R, G, B;

        int value;
        int pos, pixColor;
        int newR, newG, newB;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixColor = dst[pos];
                R = Color.red(pixColor);
                G = Color.green(pixColor);
                B = Color.blue(pixColor);

                value = R < 128 ? R : 256 - R;
                newR = (value * value * value) / 64 / 256;
                newR = (R < 128 ? newR : 255 - newR);

                value = G < 128 ? G : 256 - G;
                newG = (value * value) / 128;
                newG = (G < 128 ? newG : 255 - newG);

                newB = B / 2 + 0x25;

                // ==========边缘黑暗==============//
                dx = cx - x;
                dy = cy - y;
                if (width > height) {
                    dx = (dx * ratio) >> 15;
                } else {
                    dy = (dy * ratio) >> 15;
                }
                distSq = dx * dx + dy * dy;
                if (distSq > min) {
                    v = ((max - distSq) << 8) / diff;
                    v *= v;

                    ri = newR * v >> 16;
                    gi = newG * v >> 16;
                    bi = newB * v >> 16;

                    newR = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
                    newG = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
                    newB = bi > 255 ? 255 : (bi < 0 ? 0 : bi);
                }
                // ==========边缘黑暗end==============//

                dst[pos] = Color.rgb(newR, newG, newB);
            }
        }

        Bitmap acrossFlushBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        acrossFlushBitmap.setPixels(dst, 0, width, 0, 0, width, height);
        return acrossFlushBitmap;
    }
    /**
     * 暖意特效
     *
     * @param bmp 原图片
     * @param centerX 光源横坐标
     * @param centerY 光源纵坐标
     * @return
     */
    /**
     * 暖意特效
     *
     * @param bmp      原图片
     * @param centerX  光源横坐标
     * @param centerY  光源纵坐标
     * @param strength 光照强度 100~200 可以更高，但是效果不好
     * @return
     */
    public static Bitmap warmthFilter(Bitmap bmp, int centerX, int centerY, @FloatRange(from = 100, to = 200) float strength) {
        final int width = bmp.getWidth();
        final int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int radius = Math.min(centerX, centerY);

        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = pixR;
                newG = pixG;
                newB = pixB;

                // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(centerX - k, 2));
                if (distance < radius * radius) {
                    // 按照距离大小计算增加的光照值
                    int result = (int) (strength * (1.0 - Math.sqrt(distance) / radius));
                    newR = pixR + result;
                    newG = pixG + result;
                    newB = pixB + result;
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 模糊图片
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src     源图片
     * @param scale   缩放比例(0...1)
     * @param radius  模糊半径(0...25)
     * @param recycle 是否回收
     * @return 模糊后的图片
     */
    public static Bitmap getBitmapfastBlur(final Bitmap src,
                                           @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                           @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius,
                                           final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap scaleBitmap =
                Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas();
        PorterDuffColorFilter filter = new PorterDuffColorFilter(
                Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.scale(scale, scale);
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scaleBitmap = renderScriptBlur(scaleBitmap, radius, recycle);
        } else {
            scaleBitmap = stackBlur(scaleBitmap, (int) radius, recycle);
        }
        if (scale == 1) return scaleBitmap;
        Bitmap ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true);
        if (scaleBitmap != null && !scaleBitmap.isRecycled()) scaleBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * renderScript 模糊图片
     * <p>API 大于 17</p>
     *
     * @param src     源图片
     * @param radius  模糊半径(0...25)
     * @param recycle 是否回收
     * @return 模糊后的图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap renderScriptBlur(final Bitmap src,
                                          @FloatRange(
                                                  from = 0, to = 25, fromInclusive = false
                                          ) final float radius,
                                          final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        RenderScript rs = null;
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        try {
            rs = RenderScript.create(k.app());
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input = Allocation.createFromBitmap(rs,
                    ret,
                    Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            blurScript.setInput(input);
            blurScript.setRadius(radius);
            blurScript.forEach(output);
            output.copyTo(ret);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return ret;
    }

    /**
     * stack 模糊图片
     *
     * @param src     源图片
     * @param radius  模糊半径
     * @param recycle 是否回收
     * @return stack 模糊后的图片
     */
    public static Bitmap stackBlur(final Bitmap src, final int radius, final boolean recycle) {
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        if (radius < 1) {
            return null;
        }

        int w = ret.getWidth();
        int h = ret.getHeight();

        int[] pix = new int[w * h];
        ret.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        ret.setPixels(pix, 0, w, 0, 0, w, h);
        return ret;
    }


}
