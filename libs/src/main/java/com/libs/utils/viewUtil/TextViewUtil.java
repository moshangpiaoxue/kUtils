package com.libs.utils.viewUtil;

import android.graphics.Paint;
import android.widget.TextView;

import com.libs.k;
import com.libs.utils.dataUtil.StringUtil;


/**
 * Created by Administrator on 2017/6/27 0027.
 * textview 工具类
 */

public class TextViewUtil {
    /**
     * 设置删除线
     *
     * @param textView
     */
    public static void setDeleteLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 设置下划线
     */
    public static void setUnderline(TextView textView, String str) {
        if (!StringUtil.isEmpty(str)){
            textView.setText(str);
        }

        //下划线
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textView.getPaint().setAntiAlias(true);
    }

    /**
     * 设置字体颜色
     *
     * @param textView
     * @param colorId
     */
    public static void setColor(TextView textView, int colorId) {
        textView.setTextColor(k.app().getResources().getColor(colorId));
    }

    /**
     * 设置首行缩进
     */
    public static void setTextIndent(TextView textView, String str) {
        textView.setText(StringUtil.getString("\u3000\u3000", str));
    }

    /**
     * 倒计时
     *
     * @param textView 控件
     * @param waitTime 倒计时总时长
     * @param interval 倒计时的间隔时间
     * @param hint     倒计时完毕时显示的文字
     */
    public static void countDown(final TextView textView, long waitTime, long interval, final String hint) {
        textView.setEnabled(false);
        android.os.CountDownTimer timer = new android.os.CountDownTimer(waitTime + 1050, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("剩下 " + ((millisUntilFinished / 1000) - 1) + " S");
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText(hint);
            }
        };
        timer.start();
    }
}
