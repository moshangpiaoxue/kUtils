package com.libs.modle.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能： fragmentPagerAdapter==适用于那些相对静态的页，数量也比较少的那种;
 * Fragment对象会一直存留在内存中，所以当有大量的显示页时，就不适合用FragmentPagerAdapter了，
 * FragmentPagerAdapter 适用于只有少数的page情况，像选项卡
 * 该类内的每一个生成的 Fragment 都将保存在内存之中
 */

public class fragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    /**
     * 构造方法1
     *
     * @param fm 碎片管理器
     */
    public fragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 构造方法2
     *
     * @param fm           碎片管理器
     * @param fragmentList 碎片集合
     */
    public fragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
