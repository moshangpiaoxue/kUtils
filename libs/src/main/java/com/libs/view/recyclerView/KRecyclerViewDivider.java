package com.libs.view.recyclerView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * description: recycleview分隔符
 * autour: mo
 * date: 2017/9/22 0022 19:16
*/
public class KRecyclerViewDivider extends RecyclerView.ItemDecoration {

    private int space = 0;
    private int leftSpace = 0;
    private int rightSpace = 0;
    private int topSpace = 0;
    private int bottomSpace = 0;
    private boolean isTop = false;

    public KRecyclerViewDivider(int leftSpace, int rightSpace, int topSpace, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
    }

    public KRecyclerViewDivider(int leftSpace, int rightSpace, int topSpace, int bottomSpace, boolean isTop) {
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.isTop = isTop;
    }

    public KRecyclerViewDivider(int space, boolean isTop) {
        this.space = space;
        this.isTop = isTop;
    }

    public KRecyclerViewDivider(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (space == 0) {
            outRect.left = leftSpace;
            outRect.right = rightSpace;
            outRect.bottom = topSpace;
            outRect.top = bottomSpace;
        } else {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        }

        if (isTop) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = 0;
            }
            outRect.left = 0;
            outRect.right = 0;
        }
    }

}