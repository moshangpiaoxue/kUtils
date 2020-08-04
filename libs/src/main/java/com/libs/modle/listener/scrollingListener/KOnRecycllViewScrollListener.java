package com.libs.modle.listener.scrollingListener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @ author：mo
 * @ data：2018/1/2 0002
 * @ 功能：RecyclerView的滑动监听
 */
public abstract class KOnRecycllViewScrollListener extends RecyclerView.OnScrollListener {
    public static enum LAYOUT_MANAGER_TYPE {
        /**
         * 线性
         */
        LINEAR,
        /**
         * 表格
         */
        GRID,
        /**
         * 瀑布流
         */
        STAGGERED_GRID,
        /** 自定义 */
        CUSTOM

    }

    /**
     * layoutManager的类型（枚举）
     */
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 当前可见第一个可见的item的位置
     */
    private int firstVisibleItemPosition;

    /**
     * 当前可见最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;
    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;


    /**
     * 滚动时回调
     *
     * @param recyclerView 当前滚动的view
     * @param dx           水平滚动距离
     *                     <p>
     *                     dx > 0 时为手指向左滚动,列表滚动显示右面的内容
     *                     dx < 0 时为手指向右滚动,列表滚动显示左面的内容
     *                     <p>
     * @param dy           垂直滚动距离
     *                     dy > 0 时为手指向上滚动,列表滚动显示下面的内容
     *                     dy < 0 时为手指向下滚动,列表滚动显示上面的内容
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //  int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                layoutManagerType = LAYOUT_MANAGER_TYPE.CUSTOM;
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case GRID:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                firstVisibleItemPosition = findMix(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);

                break;
            default:
                break;
        }


    }

    /**
     * 滚动状态变化时回调
     *
     * @param recyclerView 当前在滚动的RecyclerView
     * @param newState     当前滚动状态.
     *                     SCROLL_STATE_IDLE = 0;静止没有滚动
     *                     SCROLL_STATE_DRAGGING = 1;正在被外部拖拽,一般为用户正在用手指滚动
     *                     SCROLL_STATE_SETTLING = 2;自动滚动 处于滑动状态  调用 scrollToPosition(int) 应该会触发这个状态
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        doWhat((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)
                , firstVisibleItemPosition, lastVisibleItemPosition, currentScrollState);


    }

    /**
     * 干什么
     *
     * @param isBottom                 是否到底了
     * @param firstVisibleItemPosition 当前可见的第一个item的位置
     * @param lastVisibleItemPosition  当前可见的最后一个item的位置
     * @param scrollState              scrollState=SCROLL_STATE_IDLE=0：当前的recycleView不滑动(滑动已经停止时)，
     *                                 scrollState=SCROLL_STATE_DRAGGING =1：当前的recycleView被拖动滑动，
     *                                 scrollState=SCROLL_STATE_SETTLING  =2：当前的recycleView在滚动到某个位置的动画过程,但没有被触摸滚动.调用 scrollToPosition(int) 应该会触发这个状态
     */
    protected abstract void doWhat(boolean isBottom, int firstVisibleItemPosition, int lastVisibleItemPosition, int scrollState);

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMix(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value < max) {
                max = value;
            }
        }
        return max;
    }
}
