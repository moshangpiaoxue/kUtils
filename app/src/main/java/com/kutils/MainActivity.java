package com.kutils;

import com.kutils.UtilsActivtys.aUtilActivity;
import com.kutils.ViewActivitys.aViewActivity;
import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("首页");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("View相关", aViewActivity.class));
        list.add(new MainBean("Utils相关", aUtilActivity.class));
        refeshAdapter(list);
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<MainBean> getAdapter() {
        return AdapterModle.getMainAdapter(mActivity, mData);
    }

}
