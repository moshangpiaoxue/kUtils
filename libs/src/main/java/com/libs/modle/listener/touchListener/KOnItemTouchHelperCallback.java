package com.libs.modle.listener.touchListener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.libs.utils.ResUtil;


/**
 * @ author：mo
 * @ data：2018/12/13：17:10
 * @ 功能：ItemTouchHelper 回调
 * @ 使用：
 * 创建一个ItemTouchHelper，需要传入一个继承自ItemTouchHelper.Callback
 * ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new KOnItemTouchHelperCallback(adapter));
 * itemTouchHelper.attachToRecyclerView(recyclerView);
 */
public abstract class KOnItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private KOnItemTouchMoveListener mKOnItemTouchMoveListener;

    public KOnItemTouchHelperCallback(KOnItemTouchMoveListener listener) {
        this.mKOnItemTouchMoveListener = listener;
    }

    /**
     * 设置RecyclerView支持拖动和滑动的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        //支持上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        //支持左右滑动
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        int flags = makeMovementFlags(dragFlags, swipeFlag);
        return flags;
    }

    /**
     * 当上下拖动的时候调用该方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            // 当item的类型不一样的时候不能交换
            return false;
        }

        boolean result = mKOnItemTouchMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        boolean result = mKOnItemTouchMoveListener.onItemMove(viewHolder.getLayoutPosition(), target.getLayoutPosition());

        return result;
    }

    /**
     * 当左右滑动的时候调用该方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mKOnItemTouchMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * 选中状态改变时监听
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //不是空闲状态
            viewHolder.itemView.setBackgroundColor(ResUtil.getColor("#333333"));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 恢复item状态
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        super.clearView(recyclerView, viewHolder);
    }

    /**
     * holde ItemView绘制，属性动画
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //在左右滑动时，让item的透明度随着移动而改变，并缩放
        float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);

        }
        //防止item复用出现问题，如果大家不理解下面这段代码，可以自行注释效果，
        if (alpha <= 0) {
            viewHolder.itemView.setAlpha(1);
            viewHolder.itemView.setScaleX(1);
            viewHolder.itemView.setScaleY(1);

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}