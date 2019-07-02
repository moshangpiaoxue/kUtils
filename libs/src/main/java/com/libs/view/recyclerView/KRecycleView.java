package com.libs.view.recyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;


/**
 * description: 自定义recycleview，用于免设置一些属性
 * autour: mo
 * date: 2017/9/5 0005 11:38
 */
public class KRecycleView extends RecyclerView {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 是否滚动，为了处理当scrrolview嵌套recyclerview的时候数据显示不全
     */
    private Boolean scroolSta = true;
    /**
     * 布局管理器 分三种，线性，表格和瀑布流
     */
    private LayoutManager layoutManager = null;

    public KRecycleView(Context context) {
        super(context);
        mContext = context;
        setLayoutLinerVertical();
    }

    /**
     * 设置竖直线性布局
     */
    public void setLayoutLinerVertical() {
        if (layoutManager == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //解决数据加载不完的问题
            this.setNestedScrollingEnabled(false);
            this.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            this.setFocusable(false);
            this.setLayoutManager(linearLayoutManager);//跟scrollview兼容
        }

    }

    public KRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayoutLinerVertical();
    }

    public KRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setLayoutLinerVertical();
    }

    /**
     * 设置recycleview是否可以滚动
     *
     * @param state
     */
    public void setScroolSta(Boolean state) {
        scroolSta = state;
    }

    /**
     * 设置某一项置顶
     *
     * @param position
     */
    public void setItemToTop(int position) {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(position, 0);
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) getLayoutManager()).scrollToPositionWithOffset(((GridLayoutManager) getLayoutManager()).getSpanCount() / position, 0);
        }
//        else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
//            ((StaggeredGridLayoutManager) getLayoutManager()).scrollToPositionWithOffset(((StaggeredGridLayoutManager) getLayoutManager()).getSpanCount()/position, 0);
//        }
    }

    /**
     * 获取当前列表所用的布局管理器
     */
    @Override
    public LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        设置不滚动
        if (!scroolSta) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 设置竖直线性布局--设置间距
     */
    public void setLayoutLinerVertical(int space) {
        if (layoutManager == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.setNestedScrollingEnabled(false);
            this.setLayoutManager(linearLayoutManager);//跟scrollview兼容
            this.addItemDecoration(new KRecyclerViewDivider(space));
        }
    }

    /**
     * 设置水平线性布局
     */
    public void setLayoutLinerHorizontal() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置表格布局
     *
     * @param spanCount 列数
     *                  item布局高度设为wrap_content
     */
    public void setLayoutGrid(final int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        this.setLayoutManager(gridLayoutManager);
        //设置间距
        this.addItemDecoration(new ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 6;
                outRect.bottom = 6;
                //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
                if (parent.getChildLayoutPosition(view) % spanCount == 0) {
                    outRect.left = 0;
                }
            }
        });
    }

    public void setLayoutGrid(final int spanCount, final int space) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        this.setLayoutManager(gridLayoutManager);
        //设置间距
        this.addItemDecoration(new ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
//                //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
                if (parent.getChildLayoutPosition(view) % spanCount == 0) {
                    outRect.left = 0;
                }
//                if (parent.getChildLayoutPosition(view) == 0) {
//                    outRect.top = space;
//                } else {
//                    outRect.top = space;
//                }
            }
        });
    }

    /**
     * 设置瀑布流布局
     *
     * @param spanCount 列数
     */
    public void setLayoutGridStaggered(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        this.setLayoutManager(staggeredGridLayoutManager);
    }

    /**
     * 获取recycleview当前显示部分的高度
     *
     * @return
     */
    public int getDisplayHight() {
        return this.computeVerticalScrollExtent();
    }

    /**
     * 这个方法用来判断 水平方向的滑动
     *
     * @param direction
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        return super.canScrollHorizontally(direction);
    }

    /**
     * 判断View在竖直方向是否还能向上，向下滑动。
     * 可判断是否到顶或触底
     * RecyclerView.canScrollVertically(1)的值表示是否能向下滚动，false表示已经滚动到底部
     * RecyclerView.canScrollVertically(-1)的值表示是否能向上滚动，false表示已经滚动到顶部
     *
     * @param direction -1 表示手指向下滑动（屏幕向上滑动）， 1 表示手指向上滑动（屏幕向下滑动）。
     * @return
     */
    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }

}
