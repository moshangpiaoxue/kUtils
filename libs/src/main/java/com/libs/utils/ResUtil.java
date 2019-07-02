package com.libs.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.libs.KApplication;
import com.libs.k;
import com.libs.utils.systemUtils.exceptionUtil.ExceptionUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * @ author：mo
 * @ data：2018/1/29：13:44
 * @ 功能：res工具类
 */
public class ResUtil {
    private ResUtil() {
        throw ExceptionUtil.getUnsupportedOperationException();
    }

    private static TypedValue mTmpValue = new TypedValue();

    /**
     * 获取尺寸大小(暂时当获取dp使用)
     * <p> 传参为17
     * 使用getDimension：获取的width为17*1.5=25.5px,也就是说getDimension返回的是屏幕像素大小
     * 使用getDimensionPixelOffset：返回25px,也就是说会去掉float后面的小数点
     * 使用getDimensionPixelSize：返回26px，四舍五入之后得到的数值
     *
     * @param id
     * @return
     */
    public static int getDimenDp(@DimenRes int id) {
        Resources r = k.app().getResources();
        int dimension = r.getDimensionPixelSize(id);
        return dimension;
    }

    /**
     * 获取尺寸大小(暂时当获取sp使用)
     *
     * @param id
     * @return
     */
    public static int getDimenSp(int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            k.app().getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }

    /**
     * 获取颜色值
     *
     * @param colorId
     * @return
     */
    @ColorInt
    public static int getColor(@ColorRes @NonNull int colorId) {
        return ContextCompat.getColor(k.app(), colorId);
    }

    /**
     * 获取颜色值
     *
     * @param colorStr
     * @return
     */
    public static int getColor(String colorStr) {
        return Color.parseColor(colorStr);
    }

    /**
     * 获取bitmap
     *
     * @param resId
     * @return
     */
    public static Bitmap getBitmap(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(k.app(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return k.app().getResources();
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 根据id拿到Drawable
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(k.app(), resId);
    }

    /**
     * 根据layout文件的名字取得id
     *
     * @param name
     * @return
     */
    public static int getLayoutId(String name) {
        return k.app().getResources().getIdentifier(name, "layout",
                k.app().getPackageName());
    }

    /**
     * 根据string的名字取得id
     *
     * @param name
     * @return
     */
    public static int getStringId(String name) {
        return k.app().getResources().getIdentifier(name, "string",
                k.app().getPackageName());
    }

    /**
     * 根据drawable文件的名字取得id
     *
     * @param name
     * @return
     */
    public static int getDrawableId(String name) {
        return k.app().getResources().getIdentifier(name,
                "drawable", k.app().getPackageName());
    }

    /**
     * 根据style的名字取得id
     *
     * @param name
     * @return
     */
    public static int getStyleId(String name) {
        return k.app().getResources().getIdentifier(name,
                "style", k.app().getPackageName());
    }

    /**
     * 根据id的名字取得id
     *
     * @param name
     * @return
     */
    public static int getId(String name) {
        return k.app().getResources().getIdentifier(name, "id", k.app().getPackageName());
    }

    /**
     * 根据color文件的名字取得id
     *
     * @param name
     * @return
     */
    public static int getColorId(String name) {
        return k.app().getResources().getIdentifier(name,
                "color", k.app().getPackageName());
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 根据array的名字取得id
     *
     * @param name
     * @return
     */
    public static int getArrayId(String name) {
        return k.app().getResources().getIdentifier(name,
                "array", k.app().getPackageName());
    }

    /**
     * 获取Assets文件下的数据
     *
     * @param assName 文件名
     * @return
     */
    public static String getgetAssetsString(String assName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String str = null;
        try {
            InputStream is = k.app().getResources().getAssets().open(assName);
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            is.close();
            outputStream.close();
            str = outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return k.app().getPackageName();
    }

    /**
     * 安全的执行一个任务
     *
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();
        // 如果当前线程是主线程
        if (curThreadId == getMainThreadId()) {
            task.run();
        } else {
            // 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 得到主线程id
     *
     * @return
     */
    public static long getMainThreadId() {
        return KApplication.getMainThreadId();
    }

    /**
     * 得到主线程Handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return KApplication.getMainHandler();
    }

    /**
     * 延迟执行任务
     *
     * @param task
     * @param delayMillis
     */
    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     */
    public static void removeTask(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }
}
