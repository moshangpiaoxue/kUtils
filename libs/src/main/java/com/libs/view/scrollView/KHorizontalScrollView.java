package com.libs.view.scrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by Wicky on 2015/3/26.
 * SyncHorizontalScrollView
 */
public class KHorizontalScrollView extends HorizontalScrollView {
    private View mView;

    public KHorizontalScrollView(Context context) {
        super(context);
    }

    public KHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //设置控件滚动监听，得到滚动的距离，然后让传进来的view也设置相同的滚动具体
        if(null != mView){
            mView.scrollTo(l, t);}
    }
    /**
     * 设置跟它联动的view
     * @param mView
     */
    public void setScrollView(View mView) {
        this.mView = mView;
    }
}
