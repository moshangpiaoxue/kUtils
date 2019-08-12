package com.libs.utils.task;

import android.os.CountDownTimer;

/**
 * @ author：mo
 * @ data：2018/11/7
 * @ 功能：自定义倒计时(CountDownTimer在获取系统时间的、时会有误差，当持续时间长的时候，可能会显示-1)
 */
public abstract class KCountDownTimer extends CountDownTimer {
    /**
     * 构造器
     *
     * @param millisInFuture    从当前时间开始 作用区间 如：5000
     * @param countDownInterval 间隔时间 如：1000
     */
    public KCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture + 1050, countDownInterval);
    }

    /**
     * 倒计时开始
     */
    @Override
    public void onTick(long millisUntilFinished) {
        int second = (int) Math.round((double) millisUntilFinished / 1000) - 1;
        if (second < 0) {
            onFinish();
        } else {
            onTicks(millisUntilFinished, second);
        }
    }


    /**
     * 倒计时开始 抽象方法
     *
     * @param millisUntilFinished 毫秒数
     * @param second              秒数
     */
    protected abstract void onTicks(long millisUntilFinished, int second);

    @Override
    public void onFinish() {

    }
}
