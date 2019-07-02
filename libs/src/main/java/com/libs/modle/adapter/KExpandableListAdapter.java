package com.libs.modle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.libs.modle.viewHolder.KListViewHolder;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2018/4/29：14:27
 * @ 功能：通用可扩展的下拉列表适配器
 */
public abstract class KExpandableListAdapter<T1, T2> extends BaseExpandableListAdapter {
    private Context mContext;
    /**
     * 组列表数据
     */
    private List<T1> mGroup;
    /**
     * 子列表数据
     */
    private List<List<T2>> mChild;

    public KExpandableListAdapter(Context mContext, List<T1> mGroup, List<List<T2>> mChild) {
        this.mContext = mContext;
        this.mGroup = mGroup;
        this.mChild = mChild;
    }


    /**
     * 全部刷新
     *
     * @param mGroup
     * @param mChild
     */
    public void refresh(List<T1> mGroup, List<List<T2>> mChild) {
        this.mGroup.clear();
        this.mChild.clear();
        if (mGroup != null & mGroup.size() != 0 && mChild != null & mChild.size() != 0) {
            this.mGroup.addAll(mGroup);
            this.mChild.addAll(mChild);
        }
        notifyDataSetChanged();

    }

    /**
     * 加载更多
     *
     * @param mGroup
     * @param mChild
     */
    public void loadMore(List<T1> mGroup, List<List<T2>> mChild) {
        if (this.mGroup == null) {
            this.mGroup = new ArrayList<>();
        } else if (this.mChild == null) {
            this.mChild = new ArrayList<>();
        } else if (mGroup != null & mGroup.size() != 0 && mChild != null & mChild.size() != 0) {
            this.mGroup.addAll(mGroup);
            this.mChild.addAll(mChild);
        }
        notifyDataSetChanged();
    }

    /**
     * 获得父项的数量
     */
    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    /**
     * 获得某个父项的子项数目
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChild.get(groupPosition).size();
    }

    /**
     * 获得某个父项
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    /**
     * 获得某个父项的某个子项
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChild.get(groupPosition).get(childPosition);
    }

    /**
     * 获得某个父项的id
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获得某个父项的某个子项的id
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获得父项显示的view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        KListViewHolder holder ;
        if (convertView == null) {
            holder = KListViewHolder.getViewHolder(mContext, convertView, parent, getGroupLayoutId(), groupPosition);
        } else {
            holder = (KListViewHolder) convertView.getTag();
        }

        doWhatGroup(holder, groupPosition, isExpanded, mGroup.get(groupPosition));

        return holder.getConvertView();
    }

    /**
     * 父列表交互
     *
     * @param holder        控件
     * @param groupPosition 位置
     * @param isExpanded    是否展开了
     * @param t1            当前数据
     */
    protected abstract void doWhatGroup(KListViewHolder holder, int groupPosition, boolean isExpanded, T1 t1);

    /**
     * 获取父view的布局
     *
     * @return
     */
    protected abstract int getGroupLayoutId();

    /**
     * 获得子项显示的view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        KListViewHolder holder ;
        if (convertView == null) {
            holder = KListViewHolder.getViewHolder(mContext, convertView, parent, getChildLayoutId(), groupPosition);
        } else {
            holder = (KListViewHolder) convertView.getTag();
        }
        doWhatChild(holder, groupPosition, childPosition, isLastChild, mGroup.get(groupPosition), mChild.get(groupPosition), mChild.get(groupPosition).get(childPosition));
        return holder.getConvertView();
    }

    /**
     * 子列表交互
     *
     * @param holder        控件
     * @param groupPosition 当前子view的父控件的位于父列表列表的位置
     * @param childPosition 当前view位于子view列表的位置
     * @param isLastChild   是否最后一个
     * @param t1            对应的父级数据
     * @param t2s           子列表
     * @param t2            当前数据
     */
    protected abstract void doWhatChild(KListViewHolder holder, int groupPosition, int childPosition, boolean isLastChild, T1 t1, List<T2> t2s, T2 t2);

    /**
     * 获取子view的布局
     *
     * @return
     */
    protected abstract int getChildLayoutId();

    /**
     * 子项是否可选中，如果需要设置子项的点击事件，需要返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
