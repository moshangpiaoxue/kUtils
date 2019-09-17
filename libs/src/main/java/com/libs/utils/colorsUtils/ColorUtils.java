package com.libs.utils.colorsUtils;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.TypedValue;

import com.libs.R;
import com.libs.k;


/**
 * @ author：mo
 * @ data：2018/12/12
 * @ 功能：颜色相关；如 解析颜色 / 覆盖颜色 / 抽取颜色 / 混合颜色 等
 */
public class ColorUtils {
    /**
     * 混合颜色
     */
    public static int compositeColors(@ColorInt int foreground, @ColorInt int background) {
        return android.support.v4.graphics.ColorUtils.compositeColors(foreground, background);
    }

    /**
     * 设置颜色的alpha值
     *
     * @param color    需要被设置的颜色值
     * @param alpha    取值为[0,1]，0表示全透明，1表示不透明
     * @param override 覆盖原本的 alpha
     * @return 返回改变了 alpha 值的颜色值
     */
    public static int getColor(@ColorInt int color, @FloatRange(from = 0, to = 1) float alpha, boolean override) {
        int origin = override ? 0xff : (color >> 24) & 0xff;
        return color & 0x00ffffff | (int) (alpha * origin) << 24;
    }

    /**
     * 覆盖颜色, 在背景颜色上覆盖一层颜色(有透明度)后得出的混合颜色
     * <p>注意: 覆盖的颜色需要有一定的透明度, 如果覆盖的颜色不透明, 得到的颜色为覆盖的颜色
     *
     * @param baseColor  基础颜色(背景颜色)
     * @param coverColor 覆盖颜色(前景颜色)
     * @return
     */
    public static int coverColor(@ColorInt int baseColor, @ColorInt int coverColor) {
        int fgAlpha = Color.alpha(coverColor);
        int bgAlpha = Color.alpha(baseColor);
        int a = blendColorNormalFormula(255, bgAlpha, fgAlpha, bgAlpha);
        int r = blendColorNormalFormula(Color.red(coverColor), Color.red(baseColor), fgAlpha, bgAlpha);
        int g = blendColorNormalFormula(Color.green(coverColor), Color.green(baseColor), fgAlpha, bgAlpha);
        int b = blendColorNormalFormula(Color.blue(coverColor), Color.blue(baseColor), fgAlpha, bgAlpha);
        return Color.argb(a, r, g, b);
    }

    /**
     * 抽取颜色 (覆盖颜色的相反操作), 用于猜测何种颜色覆盖上需要抽取的颜色会得出基准颜色
     * <p>注意:
     * <br>1.抽取的颜色需要有一定的透明度
     * <br>2.可能无法准确得出抽取后的颜色 (某个颜色范围内覆盖上需要抽取的颜色都可以得到基准颜色, 默认取颜色值最低的数值)
     * <br>3.可能无法得出抽取后的颜色 (任何颜色覆盖上需要抽取的颜色都无法得到基准颜色, 会报错)
     *
     * @param baseColor      基础颜色
     * @param extractedColor 需要抽取掉的颜色
     * @return 抽取后得到的颜色
     */
    public static int extractColor(@ColorInt int baseColor, @ColorInt int extractedColor) {
        int fgAlpha = Color.alpha(extractedColor);
        int mixedAlpha = Color.alpha(baseColor);
        int bgAlpha = (int) Math.round(Math.sqrt((mixedAlpha - fgAlpha) / (255 - fgAlpha)) * 255);
        int bgRed = (int) ((Color.red(baseColor) * 255 - Color.red(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        int bgGreed = (int) ((Color.green(baseColor) * 255 - Color.green(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        int bgBlue = (int) ((Color.blue(baseColor) * 255 - Color.blue(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        return Color.argb(bgAlpha, bgRed, bgGreed, bgBlue);
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        k.app().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色色值
     * @return 十六进制表示的字符串颜色
     */
    public static String getColorString(int color) {
        return String.format("#%08X", color);
//        int alpha = Color.alpha(color);
//        int red = Color.red(color);
//        int green = Color.green(color);
//        int blue = Color.blue(color);
//        StringBuilder builder = new StringBuilder("#");
//        char[] chars = "0123456789ABCDEF".toCharArray();
//        if (alpha < 255) {
//            builder.append(chars[alpha / 16]).append(chars[alpha % 16]);
//        }
//        builder.append(chars[red / 16]).append(chars[red % 16]);
//        builder.append(chars[green / 16]).append(chars[green % 16]);
//        builder.append(chars[blue / 16]).append(chars[blue % 16]);
//        return builder.toString();
    }

    /**
     * 颜色混合模式中正常模式的计算公式
     * 公式: 最终色 = 基色 * a% + 混合色 * (1 - a%)
     */
    private static int blendColorNormalFormula(int fgArgb, int bgArgb, int fgAlpha, int bgAlpha) {
        int mix = (int) (fgArgb * fgAlpha / 255f + (1 - fgAlpha / 255f) * bgArgb * bgAlpha / 255f);
        return mix > 255 ? 255 : mix;
    }

    /**
     * 根据比例，在两个color值之间计算出一个color值
     * <b>注意该方法是ARGB通道分开计算比例的</b>
     *
     * @param fromColor 开始的color值
     * @param toColor   最终的color值
     * @param fraction  比例，取值为[0,1]，为0时返回 fromColor， 为1时返回 toColor
     * @return 计算出的color值
     */
    public static int computeColor(@ColorInt int fromColor, @ColorInt int toColor, float fraction) {
        fraction = Math.max(Math.min(fraction, 1), 0);

        int minColorA = Color.alpha(fromColor);
        int maxColorA = Color.alpha(toColor);
        int resultA = (int) ((maxColorA - minColorA) * fraction) + minColorA;

        int minColorR = Color.red(fromColor);
        int maxColorR = Color.red(toColor);
        int resultR = (int) ((maxColorR - minColorR) * fraction) + minColorR;

        int minColorG = Color.green(fromColor);
        int maxColorG = Color.green(toColor);
        int resultG = (int) ((maxColorG - minColorG) * fraction) + minColorG;

        int minColorB = Color.blue(fromColor);
        int maxColorB = Color.blue(toColor);
        int resultB = (int) ((maxColorB - minColorB) * fraction) + minColorB;

        return Color.argb(resultA, resultR, resultG, resultB);
    }


}
