package com.libs.modle.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.libs.modle.listener.clickListener.KOnItemClickListener;
import com.libs.modle.listener.scrollingListener.KOnScrollingListener;
import com.libs.modle.viewHolder.KRecycleViewHolder;

import java.util.ArrayList;
import java.util.List;



/**
 * RecyclerView适配器基类
 * author   ：mo
 * data     ：2016/12/6
 * time     ：18:11
 * function :RecycleView适配器
 */

public abstract class KRecycleViewAdapter<T> extends RecyclerView.Adapter<KRecycleViewHolder> {
    /**
     * 上下文
     */
    public Context mContext;
//    public RequestManager requestManager;
    /**
     * 数据集合
     */
    protected List<T> mDatas;
    /**
     * 开始动画标识
     */
    private boolean isShow = false;
    /**
     * 屏幕中正在显示数据item的位置
     */
    private int mItemPosition;
    /**
     * 滑动监听
     */
    private KOnScrollingListener kOnScrollingListener;
    private int lastPosition = -1;
    /**
     * item出现动画
     */
    private Animation itemAnimation = null;
    /**
     * 是否延时加载动画
     */
    private boolean delayEnterAnimation = true;
    /**
     * 子项点击监听
     */
    private KOnItemClickListener listener;
    private RecyclerView parent;

    /**
     * 构造方法
     *
     * @param context
     * @param datas
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public KRecycleViewAdapter(Context context, List<T> datas) {
        mContext = context;
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
//            requestManager = Glide.with(((Activity) mContext));
        }
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<>();
        }
    }

    /**
     * 构造方法
     *
     * @param mContext 上下文
     * @param mDatas   数据集合
     * @param isShow   是否展示item出现动画
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public KRecycleViewAdapter(Context mContext, List<T> mDatas, boolean isShow) {
        this.mContext = mContext;
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
//            requestManager = Glide.with(((Activity) mContext));
        }
        this.isShow = isShow;
        if (mDatas != null) {
            this.mDatas = mDatas;
        } else {
            this.mDatas = new ArrayList<>();
        }
    }

    /**
     * 创建viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public KRecycleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (this.parent == null) {
            this.parent = (RecyclerView) parent;
        }
        KRecycleViewHolder viewHolder = null;
        if (mContext != null) {
            viewHolder = KRecycleViewHolder.getViewHolder(mContext, parent, getItemLayout(viewType));
        }
        return viewHolder;
    }


    /**
     * 绑定viewholder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final KRecycleViewHolder holder, int position) {
        mItemPosition = position;
        if (isShow) {
            setAnimation(holder.itemView, position);
        }
        holder.setItemClick(new KOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (listener != null) {
                    listener.onItemClick(view, position);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (listener != null) {
                    listener.onItemLongClick(view, position);
                }
            }
        });
        if (kOnScrollingListener != null) {
            kOnScrollingListener.onScrollingListener(mItemPosition, getItemCount());
        }

        if (position < mDatas.size()) {
            doWhat(holder, mDatas.get(position), position, getItemViewType(position), parent);

        }


    }

    /**
     * 设置动画
     *
     * @param itemView
     * @param position
     */
    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            if (itemAnimation == null) {
//                itemAnimation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.kpop_top_2_bottom_in);
                itemView.setTranslationY(1000);//相对于原始位置下方400
                itemView.setAlpha(0.f);//完全透明
//                每个item项两个动画，从透明到不透明，从下方移动到原来的位置
//                并且根据item的位置设置延迟的时间，达到一个接着一个的效果
                itemView.animate()
                        .translationY(0).alpha(1.f)
                        .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)//根据item的位置设置延迟时间，达到依次动画一个接一个进行的效果
                        .setInterpolator(new DecelerateInterpolator(0.5f))//设置动画效果为在动画开始的地方快然后慢
                        .setDuration(700)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                            }
                        })
                        .start();
            } else {
                itemView.startAnimation(itemAnimation);
            }

            lastPosition = position;
        }
    }

    /**
     * 填充数据及交互
     *
     * @param holder   view持有
     * @param bean     当前数据
     * @param position 当前位置
     * @param parent
     */
    public abstract void doWhat(KRecycleViewHolder holder, T bean, int position, int itemViewType,
                                RecyclerView parent);

    /**
     * 获取子项的type  默认是位置
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 获取数据长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    /**
     * item移除屏幕的时候清空动画
     *
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(KRecycleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    /**
     * 获取item布局
     *
     * @param viewType item数据viewType
     * @return
     */
    protected abstract int getItemLayout(int viewType);

    public void setOnItemClick(KOnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置动画开启标识
     *
     * @param isShow
     */
    public void setAnimationShow(Boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    /**
     * 设置动画
     *
     * @param itemAnimation
     */
    public void setAnimation(Animation itemAnimation) {
        this.itemAnimation = itemAnimation;
        notifyDataSetChanged();
    }


    /**
     * 设置滑动监听
     *
     * @param kOnScrollingListener
     */
    public void setOnScrollingListener(KOnScrollingListener kOnScrollingListener) {
        this.kOnScrollingListener = kOnScrollingListener;
    }

    /**
     * 获取可显示item的位置
     */
    public int getmItemPosition() {
        return mItemPosition;
    }

    /**
     * 刷新
     *
     * @param mDatas
     */
    public void refreshView(List<T> mDatas) {
        this.mDatas.clear();
        if (mDatas != null) {
            this.mDatas.addAll(mDatas);
        }
        lastPosition = -1;
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
     * 获取所有数据
     *
     * @return
     */
    public List<T> getmDatas() {
        return mDatas;
    }

}