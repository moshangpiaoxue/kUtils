package com.libs.view.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;



/**
 * description: 自定义下拉刷新view，，集成第三方，用以方便以后换
 * autour: mo
 * date: 2017/9/12 0012 14:24
 */
public class KPullToRefreshLayout extends PullToRefreshLayout {
    private Context context;

    public KPullToRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public KPullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KPullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initView(Context context) {
        this.context = context;
        this.setCanRefresh(false);
        this.setCanLoadMore(false);
    }
}
