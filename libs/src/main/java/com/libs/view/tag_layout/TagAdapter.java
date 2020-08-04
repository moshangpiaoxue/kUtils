package com.libs.view.tag_layout;


import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    /** @deprecated */
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet();

    public TagAdapter(List<T> datas) {
        this.mTagDatas = datas;
    }

    /** @deprecated */
    @Deprecated
    public TagAdapter(T[] datas) {
        this.mTagDatas = new ArrayList(Arrays.asList(datas));
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mOnDataChangedListener = listener;
    }

    /** @deprecated */
    @Deprecated
    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet();
        int[] var3 = poses;
        int var4 = poses.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int pos = var3[var5];
            set.add(pos);
        }

        this.setSelectedList((Set)set);
    }

    /** @deprecated */
    @Deprecated
    public void setSelectedList(Set<Integer> set) {
        this.mCheckedPosList.clear();
        if (set != null) {
            this.mCheckedPosList.addAll(set);
        }

        this.notifyDataChanged();
    }

    /** @deprecated */
    @Deprecated
    HashSet<Integer> getPreCheckedList() {
        return this.mCheckedPosList;
    }

    public int getCount() {
        return this.mTagDatas == null ? 0 : this.mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (this.mOnDataChangedListener != null) {
            this.mOnDataChangedListener.onChanged();
        }

    }

    public T getItem(int position) {
        return this.mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout var1, int var2, T var3);

    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }

    interface OnDataChangedListener {
        void onChanged();
    }
}
