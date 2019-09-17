package com.kutils.FrameActivitys;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kutils.R;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.view.recyclerView.KRecycleView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/9/4:14:10
 * @ 功能：
 */
public class SmartRefreshLayoutActivity extends KBaseActivity {
    private KRecycleView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    List<String> mData=new ArrayList<>();
    KRecycleViewAdapter<String> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.act_smartrefreshlayout;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
//初始化
        mRecyclerView = findViewById(R.id.rv);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        for (int i = 0; i < 5; i++) {
            mData.add("aa" + i);
        }
        adapter = AdapterModle.getAdapter(mActivity, mData);
        mRecyclerView.setAdapter(adapter);
        //设置 Header 为 贝塞尔雷达 样式
        mRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mData.clear();
                for (int i = 0; i < 10; i++) {
                    mData.add("" + i);
                }
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                for (int i = 0; i < 30; i++) {
                    mData.add("小明" + i);
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore();
            }

        });
    }
}
