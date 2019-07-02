package com.libs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libs.modle.viewHolder.ViewHolder;
import com.libs.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2017/11/19：9:54
 * @ 功能：基础Fragment
 */
public abstract class KBaseFragment extends BFragment {

    /**
     * 根view
     */
    protected View mRootView;
    /**
     * 当碎片意外关闭时，保存的一些数据
     */
    protected Bundle mBundle;
    /**
     * 判断当前碎片状态  hasStarted = false 离开碎片状态 hasStarted = true 进入碎片状态
     */
    protected boolean hasStarted = false;
    /**
     * View 管理
     */
    protected ViewHolder mViewHolder;
    /**
     * eventbus开启状态
     */
    private Boolean eventStatue = false;
    /**
     * 根view是否创建
     */
    private boolean isViewCreated;

    /**
     * 此方法在onCreateView前执行
     * 这个方法只会在 ViewPager 和 FragmentPagerAdapter一起使用时才会触发。我们可以通过 getUserVisibleHint 来得到这个状态。
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            开始界面
            hasStarted = true;
        } else {
            if (hasStarted) {
//                结束界面
                hasStarted = false;
            }
        }

        isVisibles(isVisibleToUser, isViewCreated, isVisibleToUser && isViewCreated);
    }
    /**
     * 显示状态
     *
     * @param isVisible     是否可见
     * @param isViewCreated rootview是否已创建
     * @param isView        可见并且已创建(第二次以上进入这个界面)
     */
    protected abstract void isVisibles(boolean isVisible, boolean isViewCreated, boolean isView);
    /**
     * fragment是否被隐藏
     * 当使用show/hide方法时，会触发此回调
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisibles(!hidden, isViewCreated, !hidden && isViewCreated);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewHolder = new ViewHolder(inflater, container, getLayoutId());
        mRootView = mViewHolder.getRootView();
        isViewCreated = true;
        mBundle = savedInstanceState;
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(mViewHolder, mRootView, mBundle, getArguments());
    }

    /**
     * 交互
     *
     * @param mViewHolder
     * @param mRootView
     * @param mBundle     当碎片意外关闭时，保存的一些数据
     * @param arguments   外部带进来的数据
     */
    protected abstract void initViews(ViewHolder mViewHolder, View mRootView, Bundle mBundle, Bundle arguments);

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();



    public void showToast(String string) {
        ToastUtil.showToast(string);
    }

}
