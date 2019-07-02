package com.libs.modle.listener.touchListener;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：手势监听
 */

public class KOnGestureListener implements GestureDetector.OnGestureListener {
    /**
     * 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    /**
     * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发, 注意和onDown()的区别，强调的是没有松开或者拖动的状态
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     * 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    /**
     * 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    /**
     * 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }
}
