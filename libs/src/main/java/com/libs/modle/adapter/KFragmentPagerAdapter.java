package com.libs.modle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ author：mo
 * @ data：2019/4/4：10:22
 * @ 功能：FragmentPagerAdapter 适配器 适用于较少的碎片切换，可变换tab提示
 * KFragmentPagerAdapter myAdapter = new KFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
 * vp_history.setAdapter(myAdapter);
 */
public class KFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitleList;

    public KFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public KFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    public KFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitleList) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitleList = mTitleList;
    }

    public KFragmentPagerAdapter(FragmentActivity activity, List<Fragment> mFragments, List<String> mTitleList) {
        super(activity.getSupportFragmentManager());
        this.mFragments = mFragments;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragments.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = mFragments.get(position).hashCode();
        return hashCode;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment, int index) {
        this.mFragments.add(fragment);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mFragments.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        }
        return "";
    }
}
