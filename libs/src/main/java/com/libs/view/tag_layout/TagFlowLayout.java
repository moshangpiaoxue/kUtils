package com.libs.view.tag_layout;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.libs.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private TagAdapter mTagAdapter;
    private int mSelectedMax;
    private static final String TAG = "TagFlowLayout";
    private Set<Integer> mSelectedView;
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT = "key_default";

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSelectedMax = -1;
        this.mSelectedView = new HashSet();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mSelectedMax = ta.getInt(R.styleable.TagFlowLayout_max_select, -1);
        ta.recycle();
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            TagView tagView = (TagView)this.getChildAt(i);
            if (tagView.getVisibility() != GONE && tagView.getTagView().getVisibility() == GONE) {
                tagView.setVisibility(GONE);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setAdapter(TagAdapter adapter) {
        this.mTagAdapter = adapter;
        this.mTagAdapter.setOnDataChangedListener(this);
        this.mSelectedView.clear();
        this.changeAdapter();
    }

    private void changeAdapter() {
        this.removeAllViews();
        TagAdapter adapter = this.mTagAdapter;
        TagView tagViewContainer = null;
        HashSet preCheckedList = this.mTagAdapter.getPreCheckedList();

        for(int i = 0; i < adapter.getCount(); ++i) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));
            tagViewContainer = new TagView(this.getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(-2, -2);
                lp.setMargins(dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F));
                tagViewContainer.setLayoutParams(lp);
            }

            LayoutParams lp = new LayoutParams(-1, -1);
            tagView.setLayoutParams(lp);
            tagViewContainer.addView(tagView);
            this.addView(tagViewContainer);
            if (preCheckedList.contains(i)) {
                this.setChildChecked(i, tagViewContainer);
            }

            if (this.mTagAdapter.setSelected(i, adapter.getItem(i))) {
                this.setChildChecked(i, tagViewContainer);
            }

            tagView.setClickable(false);
            final TagView finalTagViewContainer = tagViewContainer;
            final int finalI = i;
            tagViewContainer.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    TagFlowLayout.this.doSelect(finalTagViewContainer, finalI);
                    if (TagFlowLayout.this.mOnTagClickListener != null) {
                        TagFlowLayout.this.mOnTagClickListener.onTagClick(finalTagViewContainer, finalI, TagFlowLayout.this);
                    }

                }
            });
        }

        this.mSelectedView.addAll(preCheckedList);
    }

    public void setMaxSelectCount(int count) {
        if (this.mSelectedView.size() > count) {
            Log.w("TagFlowLayout", "you has already select more than " + count + " views , so it will be clear .");
            this.mSelectedView.clear();
        }

        this.mSelectedMax = count;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.mSelectedView);
    }

    private void setChildChecked(int position, TagView view) {
        view.setChecked(true);
        this.mTagAdapter.onSelected(position, view.getTagView());
    }

    private void setChildUnChecked(int position, TagView view) {
        view.setChecked(false);
        this.mTagAdapter.unSelected(position, view.getTagView());
    }

    private void doSelect(TagView child, int position) {
        if (!child.isChecked()) {
            if (this.mSelectedMax == 1 && this.mSelectedView.size() == 1) {
                Iterator<Integer> iterator = this.mSelectedView.iterator();
                Integer preIndex = (Integer)iterator.next();
                TagView pre = (TagView)this.getChildAt(preIndex);
                this.setChildUnChecked(preIndex, pre);
                this.setChildChecked(position, child);
                this.mSelectedView.remove(preIndex);
                this.mSelectedView.add(position);
            } else {
                if (this.mSelectedMax > 0 && this.mSelectedView.size() >= this.mSelectedMax) {
                    return;
                }

                this.setChildChecked(position, child);
                this.mSelectedView.add(position);
            }
        } else {
            this.setChildUnChecked(position, child);
            this.mSelectedView.remove(position);
        }

        if (this.mOnSelectListener != null) {
            this.mOnSelectListener.onSelected(new HashSet(this.mSelectedView));
        }

    }

    public TagAdapter getAdapter() {
        return this.mTagAdapter;
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key_default", super.onSaveInstanceState());
        String selectPos = "";
        if (this.mSelectedView.size() > 0) {
            int key;
            for(Iterator var3 = this.mSelectedView.iterator(); var3.hasNext(); selectPos = selectPos + key + "|") {
                key = (Integer)var3.next();
            }

            selectPos = selectPos.substring(0, selectPos.length() - 1);
        }

        bundle.putString("key_choose_pos", selectPos);
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
        } else {
            Bundle bundle = (Bundle)state;
            String mSelectPos = bundle.getString("key_choose_pos");
            if (!TextUtils.isEmpty(mSelectPos)) {
                String[] split = mSelectPos.split("\\|");
                String[] var5 = split;
                int var6 = split.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String pos = var5[var7];
                    int index = Integer.parseInt(pos);
                    this.mSelectedView.add(index);
                    TagView tagView = (TagView)this.getChildAt(index);
                    if (tagView != null) {
                        this.setChildChecked(index, tagView);
                    }
                }
            }

            super.onRestoreInstanceState(bundle.getParcelable("key_default"));
        }
    }

    public void onChanged() {
        this.mSelectedView.clear();
        this.changeAdapter();
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View var1, int var2, FlowLayout var3);
    }

    public interface OnSelectListener {
        void onSelected(Set<Integer> var1);
    }
}
