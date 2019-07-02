package com.kutils.UtilsActivtys;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.kutils.R;
import com.libs.ui.activity.KBaseLayoutActivity;
import com.libs.utils.dataUtil.SpannableStringUtil;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：SpannableStringUtil
 */
public class StringUtilsActivity extends KBaseLayoutActivity {

    @Override
    protected int getMainLayoutId() {
        return R.layout.iten;
    }


    @Override
    protected void getData() {

    }


    @Override
    protected void initViews(View mainView) {
        title.setMidleText("SpannableStringUtil");
        loadSuccess();
        TextView item_1=findViewById(R.id.item_1);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showToast("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        // 响应点击事件的话必须设置以下属性
        item_1.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableStringUtil.getBuilder("")
                .setBold()
                .setAlign(Layout.Alignment.ALIGN_CENTER)
                .addString("测试").addString("Url\n").setUrl("https://www.baidu.com/?tn=10018801_hao")
                .addString("列表项\n").setBullet(60, getResources().getColor(R.color.colorAccent))
                .addString("  测试引用\n").setQuoteColor(getResources().getColor(R.color.colorAccent))
                .addString("首行缩进\n").setLeadingMargin(30, 50)
                .addString("测试").addString("上标").setSuperscript().addString("下标\n").setSubscript()
                .addString("测试").addString("点击事件\n").setClickSpan(clickableSpan)
                .addString("测试").addString("serif 字体\n").setFontFamily("serif")
                .addString("测试").addString("图片\n").setResourceId(R.mipmap.aa)
                .addString("测试").addString("前景色").setTextColor(Color.GREEN)
                .addString("背景色\n").setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                .addString("测试").addString("删除线").setStrikethrough()
                .addString("下划线\n").setUnderline()
                .addString("测试").addString("sans-serif 字体\n").setFontFamily("sans-serif")
                .addString("测试").addString("2倍字体\n").setProportion(2)
                .addString("测试").addString("monospace字体\n").setFontFamily("monospace")
                .addString("测试").addString("普通模糊效果字体\n").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .addString("测试").addString(" 粗体 ").setBold()
                .addString(" 斜体 ").setItalic()
                .addString(" 粗斜体 \n").setBoldItalic()
                .addString("测试").addString("横向2倍字体\n").setXProportion(2)

                .addString("\n测试正常对齐\n").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .addString("测试居中对齐\n").setAlign(Layout.Alignment.ALIGN_CENTER)
                .addString("测试相反对齐\n").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .addToTextView(item_1);
    }
}
