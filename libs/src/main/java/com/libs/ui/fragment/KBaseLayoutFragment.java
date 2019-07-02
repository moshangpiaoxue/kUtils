package com.libs.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.libs.R;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.utils.bengUtil.NextOtherActivityUtil;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.viewUtil.ViewUtil;
import com.libs.view.KTitleView;


/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：基础布局碎片
 */
public abstract class KBaseLayoutFragment extends KBaseFragment {
    protected KTitleView rl_title;
    /**
     * 顶部可加头
     */
    protected LinearLayout layoutTop;
    /**
     * 底部可加尾
     */
    protected LinearLayout layoutBottom;
    /**
     * 具体展示数据
     */
    protected FrameLayout fl_base_main;
    /**
     * 空数据
     */
    protected LinearLayout layoutEmpty;
    protected ImageView viewEmptyIv;
    protected TextView viewEmptyTv;
    /**
     * 报错
     */
    protected LinearLayout layoutError;
    protected ImageView viewErrorIv;
    protected TextView viewErrorTv;
    /**
     * 没网
     */
    protected LinearLayout layoutErrorNet;
    protected ImageView viewErrorIvNet;
    protected TextView viewErrorTvNet;
    protected TextView viewErrorTvNetSetting;
    /**
     * 加载中
     */
    protected LinearLayout layoutLoading;
    protected ProgressBar viewLoadingPb;
    protected TextView viewLoadingTv;
    protected int mPage = 1;

    @Override
    protected void initViews(ViewHolder mViewHolder, View mRootView, Bundle mBundle, Bundle arguments) {
        rl_title = mViewHolder.getView(R.id.ktv_base_title);
        rl_title.setHindView();
        layoutTop = mViewHolder.getView(R.id.ll_base_top);
        layoutBottom = mViewHolder.getView(R.id.ll_base_bottom);
        fl_base_main = mViewHolder.getView(R.id.fl_base_main);
        layoutEmpty = mViewHolder.getView(R.id.ll_base_empty);
        viewEmptyIv = mViewHolder.getView(R.id.iv_base_empty);
        viewEmptyTv = mViewHolder.getView(R.id.tv_base_empty);
        layoutError = mViewHolder.getView(R.id.ll_base_error);
        viewErrorIv = mViewHolder.getView(R.id.iv_base_error);
        viewErrorTv = mViewHolder.getView(R.id.tv_base_error);
        layoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList(mPage);
            }
        });
        layoutErrorNet = mViewHolder.getView(R.id.ll_base_error_net);
        viewErrorIvNet = mViewHolder.getView(R.id.iv_base_error_net);
        viewErrorTvNet = mViewHolder.getView(R.id.tv_base_error_net);
        viewErrorTvNetSetting = mViewHolder.getView(R.id.tv_base_error_net_setting);
        layoutErrorNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextOtherActivityUtil.toSettingNet();
            }
        });
        layoutLoading = mViewHolder.getView(R.id.ll_base_loading);
        viewLoadingPb = mViewHolder.getView(R.id.pb_base_loading);
        viewLoadingTv = mViewHolder.getView(R.id.tv_base_loading);
        View mainView = ViewUtil.getView(mActivity, getMainLayoutId(), fl_base_main);
        if (mainView != null) {
            fl_base_main.addView(mainView);
        }
        initLayoutViews(mainView);
    }

    protected abstract void initLayoutViews(View mainView);


    protected abstract int getMainLayoutId();

    protected abstract void getList(int mPage);

    /**
     * 加载中
     */
    protected void loading() {
        layoutLoading.setVisibility(View.VISIBLE);
        fl_base_main.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        layoutErrorNet.setVisibility(View.GONE);
    }

    /**
     * 加载错误
     */
    protected void loadError() {
        layoutLoading.setVisibility(View.GONE);
        fl_base_main.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutError.setVisibility(View.VISIBLE);
        layoutErrorNet.setVisibility(View.GONE);
    }

    /**
     * 加载错误-没网
     */
    protected void loadErrorNet() {
        if (isCheckNet()) {
            layoutLoading.setVisibility(View.GONE);
            fl_base_main.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.GONE);
            layoutError.setVisibility(View.GONE);
            layoutErrorNet.setVisibility(View.VISIBLE);
        }
    }

    private boolean isCheckNet() {
        return false;
    }

    /**
     * 加载错误-没数据
     */
    protected void loadErrorNoData() {
        layoutLoading.setVisibility(View.GONE);
        fl_base_main.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        layoutErrorNet.setVisibility(View.GONE);
        //没有数据布局出现
        if (layoutEmpty.getVisibility() != View.VISIBLE) {
            viewEmptyIv.setImageResource(getNoDataImageRes() == 0 ? R.mipmap.load_err : getNoDataImageRes());
            viewEmptyTv.setText(StringUtil.isEmpty(getNoDataString()) ? "sorry，没有您想要的数据" : getNoDataString());

            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    protected String getNoDataString() {
        return "";
    }

    protected int getNoDataImageRes() {
        return 0;
    }

    /**
     * 加载成功
     */
    protected void loadSuccess() {
        layoutLoading.setVisibility(View.GONE);
        fl_base_main.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        layoutErrorNet.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_layout;
    }


}
