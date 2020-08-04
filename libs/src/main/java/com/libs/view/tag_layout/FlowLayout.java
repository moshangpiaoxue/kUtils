package com.libs.view.tag_layout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.libs.R;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;
    protected List<List<View>> mAllViews;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;
    private int mGravity;
    private List<View> lineViews;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mGravity = ta.getInt(R.styleable.TagFlowLayout_tag_gravity, -1);
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            View child = this.getChildAt(i);
            if (child.getVisibility() == 8) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            } else {
                this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if (lineWidth + childWidth > sizeWidth - this.getPaddingLeft() - this.getPaddingRight()) {
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }

                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            }
        }

        this.setMeasuredDimension(modeWidth == 1073741824 ? sizeWidth : width + this.getPaddingLeft() + this.getPaddingRight(), modeHeight == 1073741824 ? sizeHeight : height + this.getPaddingTop() + this.getPaddingBottom());
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mAllViews.clear();
        this.mLineHeight.clear();
        this.mLineWidth.clear();
        this.lineViews.clear();
        int width = this.getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = this.getChildCount();

        int left;
        int childWidth;
        int currentLineWidth;
        for(left = 0; left < cCount; ++left) {
            View child = this.getChildAt(left);
            if (child.getVisibility() != 8) {
                MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
                childWidth = child.getMeasuredWidth();
                currentLineWidth = child.getMeasuredHeight();
                if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - this.getPaddingLeft() - this.getPaddingRight()) {
                    this.mLineHeight.add(lineHeight);
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(lineWidth);
                    lineWidth = 0;
                    lineHeight = currentLineWidth + lp.topMargin + lp.bottomMargin;
                    this.lineViews = new ArrayList();
                }

                lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
                lineHeight = Math.max(lineHeight, currentLineWidth + lp.topMargin + lp.bottomMargin);
                this.lineViews.add(child);
            }
        }

        this.mLineHeight.add(lineHeight);
        this.mLineWidth.add(lineWidth);
        this.mAllViews.add(this.lineViews);
        left = this.getPaddingLeft();
        int top = this.getPaddingTop();
        int lineNum = this.mAllViews.size();

        for(childWidth = 0; childWidth < lineNum; ++childWidth) {
            this.lineViews = (List)this.mAllViews.get(childWidth);
            lineHeight = (Integer)this.mLineHeight.get(childWidth);
            currentLineWidth = (Integer)this.mLineWidth.get(childWidth);
            switch(this.mGravity) {
                case -1:
                    left = this.getPaddingLeft();
                    break;
                case 0:
                    left = (width - currentLineWidth) / 2 + this.getPaddingLeft();
                    break;
                case 1:
                    left = width - currentLineWidth + this.getPaddingLeft();
            }

            for(int j = 0; j < this.lineViews.size(); ++j) {
                View child = (View)this.lineViews.get(j);
                if (child.getVisibility() != 8) {
                    MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
                    int lc = left + lp.leftMargin;
                    int tc = top + lp.topMargin;
                    int rc = lc + child.getMeasuredWidth();
                    int bc = tc + child.getMeasuredHeight();
                    child.layout(lc, tc, rc, bc);
                    left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                }
            }

            top += lineHeight;
        }

    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-2, -2);
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
