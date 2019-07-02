package com.libs.view.viewPage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @ author：mo
 * @ data：2017/12/10：11:58
 * @ 功能：自定义viewpage
 */
public class KViewPage extends ViewPager {
    /**
     * 是否可以左右滑动
     */
    private boolean isCanScroll = true;

    public KViewPage(Context context) {
        super(context);
    }

    public KViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件不触发
        return isCanScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 不处理触摸拦截事件
        return isCanScroll && super.onInterceptTouchEvent(event);
    }


    /**
     * 设置是否可以滑动
     *
     * @param isCanScroll false 不能滑动， true 可以滑动
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    /**
     * 设置预加载页数
     *
     * @param num
     */
    public void setPreloadingNum(int num) {
        this.setOffscreenPageLimit(num);
    }
    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        // 分发事件，这个是必须要的，如果把这个方法覆盖了，那么ViewPager的子View就接收不到事件了
//        if (this.enabled) {
//            return super.dispatchTouchEvent(event);
//        }
//        return super.dispatchTouchEvent(event);
//    }

}
