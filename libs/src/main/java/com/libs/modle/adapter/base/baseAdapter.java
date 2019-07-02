package com.libs.modle.adapter.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：
 */

public class baseAdapter extends android.widget.BaseAdapter {
    /**
     * 要绑定的数据的数目
     *
     * @return
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * 获取指定位置的数据
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * 根据一个索引（位置）获得该位置的对象
     *
     * @param position 位置
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 获取该条目要显示的界面
     *
     * @param position    当前dataset的位置，通过getCount和getItem来使用，如果list向下滑动的话那么就是最低端的item的位置，如果是向上滑动的话那就是最上端的item的位置。
     * @param convertView 可以重用的视图，即刚刚出队的视图
     * @param parent      父布局
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
