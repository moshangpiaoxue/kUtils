package com.libs.view.tag_layout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * 标签view
 */
public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{16842912};

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return this.getChildAt(0);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (this.isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }

        return states;
    }

    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            this.refreshDrawableState();
        }

    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void toggle() {
        this.setChecked(!this.isChecked);
    }
}
