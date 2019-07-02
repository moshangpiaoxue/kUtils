package com.libs.modle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.libs.modle.viewHolder.KListViewHolder;

import java.util.List;


/**
 * @ author：mo
 * @ data：2016/11/21：14:30
 * @ 功能：通用listview适配器
 */
public abstract class KListAdapter<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据集合
     */
    protected List<T> mDatas;
    /**
     * 子项布局id
     */
    protected final int mItemLayoutId;

    /**
     * item默认选中一个
     */
    protected int mSelect = 0;

    /**
     * 构造方法
     *
     * @param context      上下文
     * @param mDatas       数据集合
     * @param itemLayoutId 子项布局id
     */
    public KListAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * 单项刷新
     *
     * @param position
     */
    public void changeSelected(int position) {
        if (position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    /**
     * 全部刷新
     *
     * @param mDatas
     */
    public void refresh(List<T> mDatas) {
        this.mDatas.clear();
        if (mDatas != null & mDatas.size() != 0) {
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();

    }

    /**
     * 加载更多
     *
     * @param mDatas
     */
    public void loadMore(List<T> mDatas) {
        if (mDatas != null & mDatas.size() != 0) {
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取项目长度
     *
     * @return
     */
    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    /**
     * 获取某一子项的数据
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {

        return mDatas != null ? mDatas.get(position) : null;
    }

    /**
     * 获取某一子项的id，默认为这个子项在数据集合中的位置
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 填充数据
     *
     * @param position    子项位于数据集合中的位置
     * @param convertView 子项的布局view
     * @param parent      父view
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        KListViewHolder holder = null;
        if (view == null) {
            holder = KListViewHolder.getViewHolder(mContext, convertView, parent, mItemLayoutId, position);
        } else {
            holder = (KListViewHolder) view.getTag();
        }
        doWhat(holder, getItem(position), position);
        return holder.getConvertView();

    }

    /**
     * 数据交互的抽象方法
     *
     * @param holder   viewholder 复用的view
     * @param bean     当前子项的数据
     * @param position 当前子项的位置
     */
    public abstract void doWhat(KListViewHolder holder, T bean, int position);

}