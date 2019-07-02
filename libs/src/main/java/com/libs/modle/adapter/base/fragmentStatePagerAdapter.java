package com.libs.modle.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：fragmentPagerAdapter==需要处理有很多页，并且数据动态性较大、占用内存较多的情况;
 * 只保留当前页面，当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，
 * 生成新的页面(就像 ListView 的实现一样)。这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 */

public class fragmentStatePagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    /**
     * 构造方法1
     *
     * @param fm 碎片管理器
     */
    public fragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 构造方法2
     *
     * @param fm           碎片管理器
     * @param fragmentList 碎片集合
     */
    public fragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * 获取指定位置的碎片
     *
     * @param position 位置
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    /**
     * 获取数据长度
     *
     * @return
     */
    @Override
    public int getCount() {
        return 0;
    }
}
