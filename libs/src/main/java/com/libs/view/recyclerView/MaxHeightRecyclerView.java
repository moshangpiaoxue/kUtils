package com.libs.view.recyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.libs.R;


/**
 * @ author：mo
 * @ data：2020/7/7:17:27
 * @ 功能：
 */
public class MaxHeightRecyclerView extends KRecycleView {
    private int mMaxHeight = -1;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView1);
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView1_maxHeight, mMaxHeight);
        arr.recycle();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpec = 0;
        if (mMaxHeight < 0) {
            heightSpec = heightMeasureSpec;
        } else {
            switch (MeasureSpec.getMode(heightMeasureSpec)) {
                case MeasureSpec.AT_MOST:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, mMaxHeight), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    heightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, mMaxHeight), MeasureSpec.EXACTLY);
                    break;
                default:
                    heightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
                    break;
            }
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
