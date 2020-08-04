package com.libs.modle.adapter;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * @ author：mo
 * @ data：2017/7/8：14:31
 * @ 功能：
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] mFragments;
    private Fragment mCurrentFragment;
    private int mCurrentFragmentPosition;
    public ViewPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        mCurrentFragmentPosition = position;
        super.setPrimaryItem(container, position, object);
    }



    /**
     * 当前显示的碎片
     *
     * @return 碎片
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 当前显示的碎片位置
     *
     * @return position
     */
    public int getCurrentFragmentPosition() {
        return mCurrentFragmentPosition;
    }
}
