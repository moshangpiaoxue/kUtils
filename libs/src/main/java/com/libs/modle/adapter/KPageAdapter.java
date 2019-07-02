package com.libs.modle.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * description: 通用pageAdapter
 * autour: mo
 * date: 2017/11/13 0013 15:14
 */
public abstract class KPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    protected Context context;
    protected int layoutId;

    public KPageAdapter(Context context, List<T> mDatas, int layoutId) {
        this.mDatas = mDatas;
        this.context = context;
        this.layoutId = layoutId;
    }

    /**
     * 要数据的长度
     *
     * @return
     */
    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    /**
     * 当要显示的数据可以进行缓存的时候，会调用这个方法进行显示数据的初始化，我们将要显示的布局控件加入到ViewGroup中，然后作为返回值返回即可
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        doWhat(container, view, position);
        container.addView(view);
        return view;

    }

    /**
     * PagerAdapter只缓存三组数据，如果滑动的数据超出了缓存的范围，就会调用这个方法，将数据销毁
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 来判断显示的是否是同一个数据，这里我们将两个参数相比较返回即可
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    protected abstract void doWhat(ViewGroup container, View view, int position);

}
