package com.libs.utils.image;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;

import com.libs.k;
import com.libs.utils.colorsUtils.ColorUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @ author：mo
 * @ data：2019/2/13:10:06
 * @ 功能：处理 Drawable 相关
 */
public class DrawableUtil {
    /**
     * 资源图片 转 Drawable
     *
     * @param resId 资源 id
     * @return Drawable 对象
     */
    public static Drawable getDrawable(int resId) {
        return k.app().getResources().getDrawable(resId);
    }

    /**
     * Bitmap 转 Drawable
     *
     * @param bitmap Bitmap 对象
     * @return Drawable 对象
     */
    public static Drawable getDrawable(Bitmap bitmap) {
        return BitmapUtil.toDrawable(bitmap);
    }
    /**
     * Drawable 转 Bitmap
     *
     * @param drawable Drawable 对象
     * @return Bitmap 对象
     */
    public static Bitmap toBitmap(Drawable drawable) {
        return BitmapUtil.getBitmap(drawable);
    }

    /**
     * 为 Drawable 添加颜色蒙层, 可用于改变状态颜色等
     *
     * @param background 背景 Drawable
     * @param color      颜色蒙层
     * @return 添加蒙层后的新的 Drawable 对象, 处理失败返回 null
     */
    public static Drawable addColorMask(Drawable background, int color) {
        if (background == null || Color.alpha(color) == 0xFF) {
            return new ColorDrawable(color);
        } else if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            int fgColor = colorDrawable.getColor();
            int pressedColor = ColorUtils.coverColor(fgColor, color);
            return new ColorDrawable(pressedColor);
        } else if (background instanceof BitmapDrawable) {
            Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
            Bitmap pressedBitmap = BitmapUtil.getBitmapColorMask(fgBitmap, color);
            return BitmapUtil.toDrawable(pressedBitmap);
        } else if (background instanceof GradientDrawable) {
            GradientDrawable pressedDr = copyGradientDrawable((GradientDrawable) background);
            if (pressedDr != null) {
                try {
                    Object gradientState = getField(pressedDr, "mGradientState");
                    if (gradientState == null) {
                        pressedDr.setColor(color);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ColorStateList solidColors = pressedDr.getColor();
                        if (solidColors != null) {
                            int normalColor = solidColors.getColorForState(new int[]{}, Color.TRANSPARENT);
                            pressedDr.setColor(ColorUtils.coverColor(normalColor, color));
                        } else {
                            int[] gradientColors = pressedDr.getColors();
                            if (gradientColors != null) {
                                for (int i = 0; i < gradientColors.length; i++) {
                                    gradientColors[i] = ColorUtils.coverColor(gradientColors[i], color);
                                }
                                pressedDr.setColors(gradientColors);
                            } else {
                                pressedDr.setColor(color);
                            }
                        }
                    } else {
                        ColorStateList solidColors = (ColorStateList) getField(gradientState, "mSolidColors");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (solidColors != null) {
                                int normalColor = solidColors.getColorForState(new int[]{}, Color.TRANSPARENT);
                                pressedDr.setColor(ColorUtils.coverColor(normalColor, color));
                            } else {
                                int[] gradientColors = (int[]) getField(gradientState, "mGradientColors");
                                if (gradientColors != null) {
                                    for (int i = 0; i < gradientColors.length; i++) {
                                        gradientColors[i] = ColorUtils.coverColor(gradientColors[i], color);
                                    }
                                    pressedDr.setColors(gradientColors);
                                } else {
                                    pressedDr.setColor(color);
                                }
                            }
                        } else {
                            if (solidColors != null) {
                                pressedDr.setColor(ColorUtils.coverColor((solidColors).getColorForState(new int[0], 0), color));
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    int[] gradientColors = (int[]) getField(gradientState, "mGradientColors");
                                    if (gradientColors != null) {
                                        for (int i = 0; i < gradientColors.length; i++) {
                                            gradientColors[i] = ColorUtils.coverColor(gradientColors[i], color);
                                        }
                                        pressedDr.setColors(gradientColors);
                                    }
                                }
                            }
                        }
                    }
                    return pressedDr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 拷贝 GradientDrawable  TODO: 17/9/15  need test case
     *
     * @param drawable 源 GradientDrawable 对象
     * @return 拷贝后的新的 GradientDrawable 对象
     */
    static GradientDrawable copyGradientDrawable(GradientDrawable drawable) {
        try {
            GradientDrawable copy = new GradientDrawable();
            Object gradientState = getField(drawable, "mGradientState");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                copy.setOrientation(drawable.getOrientation());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                copy.setAlpha(drawable.getAlpha());
                copy.setStroke((Integer) getField(gradientState, "mStrokeWidth"),
                        (ColorStateList) getField(gradientState, "mStrokeColors"),
                        (Float) getField(gradientState, "mStrokeDashWidth"),
                        (Float) getField(gradientState, "mStrokeDashGap"));
            } else {
                copy.setAlpha((Integer) getField(drawable, "mAlpha"));
                copy.setStroke((Integer) getField(gradientState, "mStrokeWidth"),
                        ((ColorStateList) getField(gradientState, "mStrokeColors")).getColorForState(new int[0], 0),
                        (Float) getField(gradientState, "mStrokeDashWidth"),
                        (Float) getField(gradientState, "mStrokeDashGap"));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                float[] cornerRadii = null;
                try {
                    cornerRadii = drawable.getCornerRadii(); // 没有设置 CornerRadii 时会导致空指针异常
                } catch (Exception ignored) {
                }
                if (cornerRadii != null) {
                    copy.setCornerRadii(cornerRadii);
                } else {
                    copy.setCornerRadius(drawable.getCornerRadius());
                }
                ColorStateList solidColors = drawable.getColor();
                if (solidColors != null) {
                    copy.setColor(solidColors);
                } else {
                    copy.setColors(drawable.getColors());
                }
                copy.setGradientRadius(drawable.getGradientRadius());
                copy.setGradientType(drawable.getGradientType());
                copy.setShape(drawable.getShape());
            } else {
                float[] cornerRadii = (float[]) getField(gradientState, "mRadiusArray");
                if (cornerRadii != null) {
                    copy.setCornerRadii(cornerRadii);
                } else {
                    copy.setCornerRadius((Float) getField(gradientState, "mRadius"));
                }
                invokeMethod(copy, "setGradientType",
                        new Class[]{int.class}, new Object[]{getField(gradientState, "mGradient")});
                invokeMethod(copy, "setShape",
                        new Class[]{int.class}, new Object[]{getField(gradientState, "mShape")});

                ColorStateList solidColors = (ColorStateList) getField(gradientState, "mSolidColors");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (solidColors != null) {
                        copy.setColor(solidColors);
                    } else {
                        copy.setColors((int[]) getField(gradientState, "mGradientColors"));
                    }
                } else {
                    if (solidColors != null) {
                        copy.setColor((solidColors).getColorForState(new int[0], 0));
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Object gradientColors = getField(gradientState, "mGradientColors");
                            if (gradientColors != null) {
                                copy.setColors((int[]) gradientColors);
                            }
                        }
                    }
                }
            }
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LshLog", "copyGradientDrawable: ", e);
        }
        return null;
    }

    private static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private static Object invokeMethod(Object obj, String methodName, Class[] parameterTypes, Object[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
}
