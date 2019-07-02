package com.libs.modle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.libs.modle.viewHolder.KRecycleViewHolder;

import java.util.List;

/**
 * @ author：mo
 * @ data：2016/12/29：14:28
 * @ 功能：多布局RecycleView适配器
 * prv_common.setAdapter(new CommonMultiRecycleViewAdapter(activity,data) {
 * @Override protected CommonRecycleViewHolder creatHolder(Context mContext, ViewGroup parent, int viewType) {
 * CommonRecycleViewHolder viewHolder = CommonRecycleViewHolder.get(mContext, parent, R.layout.item_project_complex);
 * return viewHolder;
 * }
 * @Override public void doWhat(CommonRecycleViewHolder holder, Object bean, int position) {
 * LogUtil.i("SSSSSSSSSSSSSSSSSSSSSSSSSSS" + bean.toString());
 * }
 * });
 */
public abstract class CommonMultiRecycleViewAdapter<T> extends RecyclerView.Adapter<KRecycleViewHolder> {
    private Context mContext;
    private List<T> mDatas;
    private LayoutInflater mInflater;


    public CommonMultiRecycleViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    public void refreshView(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void loadMore(List<T> mDatas) {
        if (mDatas != null & mDatas.size() != 0) {
            this.mDatas.addAll(mDatas);
            notifyDataSetChanged();
        }

    }


    @Override
    public KRecycleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        KRecycleViewHolder viewHolder = creatHolder(mContext, parent, viewType);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    protected abstract KRecycleViewHolder creatHolder(Context mContext, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(KRecycleViewHolder holder, int position) {
        doWhat(holder, mDatas.get(position), position);
    }


    public abstract void doWhat(KRecycleViewHolder holder, T bean, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}