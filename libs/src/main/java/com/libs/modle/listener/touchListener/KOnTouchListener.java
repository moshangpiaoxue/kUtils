package com.libs.modle.listener.touchListener;

import android.view.MotionEvent;
import android.view.View;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能： 触摸监听
 */
public class KOnTouchListener implements View.OnTouchListener {
    /**
     * @param view        事件源控件
     * @param motionEvent 触摸状态
     * @return 是否拦截后续操作
     * true=执行操作后到此为止，不执行view控件的其他诸如点击之类的操作   false==执行操作后，执行view控件的其他诸如点击之类的操作
     * true: 1.告诉Android，MotionEvent对象已被使用，不能再提供给其他方法。
     * 2.还告诉Android，继续将此触摸序列的触摸事件(move,up)发送到此方法。
     * false:1.告诉Android，onTouch()方法未使用该事件，所以Android寻找要调用的下一个方法。
     * 2.告诉Android。不再将此触摸序列的触摸事件（move,up）发送到此方法。
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                break;
            //抬起
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }
}
