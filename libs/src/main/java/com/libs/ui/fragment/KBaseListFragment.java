package com.libs.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.libs.R;
import com.libs.modle.adapter.KHeaderAndFooterWrapper;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.view.PullToRefresh.BaseRefreshListener;
import com.libs.view.PullToRefresh.PullToRefreshLayout;
import com.libs.view.recyclerView.KRecycleView;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：基础列表碎片
 */
public abstract class KBaseListFragment<T> extends KBaseLayoutFragment {
    protected LinearLayout ll_base_list_addlayout;
    protected PullToRefreshLayout kPullLayout;
    protected KRecycleView kRecycleview;
    protected List<T> mData = new ArrayList<T>();
    protected KHeaderAndFooterWrapper<T> mWrapper;
    protected KRecycleViewAdapter<T> mAdapter;

    @Override
    protected void initLayoutViews(View mainView) {
        ll_base_list_addlayout = mainView.findViewById(R.id.ll_base_list_addlayout);
        kPullLayout = mainView.findViewById(R.id.k_pull_layout);
        kRecycleview = mainView.findViewById(R.id.k_recycleview);
        kPullLayout.setCanRefresh(false);
        kPullLayout.setCanLoadMore(false);
        kPullLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                mPage = 1;
                getList(mPage);
            }

            @Override
            public void loadMore() {
                getMore(mPage);
            }
        });
        mAdapter = getAdapter();
        if (mAdapter != null) {
            mWrapper = new KHeaderAndFooterWrapper<T>(mAdapter);
            kRecycleview.setAdapter(mWrapper);
        }

        loading();
        initListView();
        if (isCanAutoRefresh()) {
            kPullLayout.autoRefresh();
        }
    }

    /**
     * 是否自动刷新
     */
    protected boolean isCanAutoRefresh() {
        return true;
    }

    protected abstract void initListView();

    public void loadList(List<T> list) {
        if (list == null || list.size() == 0) {
            loadErrorNoData();
        } else {
            loadSuccess();
            mData.clear();
            mData.addAll(list);
            mWrapper.notifyDataSetChanged();
            mPage++;
        }
        kPullLayout.finishRefresh();
    }

    public void loadMore(List<T> list) {
        if (list == null || list.size() == 0) {
            noMoreData();
            mPage--;
        } else {
            mData.addAll(list);
            mWrapper.notifyDataSetChanged();
            mPage++;
        }
        kPullLayout.finishLoadMore();
    }

    private void noMoreData() {
        showToast("没有更多数据");
        kPullLayout.setCanLoadMore(false);
    }

    protected abstract KRecycleViewAdapter<T> getAdapter();

    protected abstract void getMore(int mPage);

    @Override
    protected int getMainLayoutId() {
        return R.layout.base_list_layout;
    }


}
