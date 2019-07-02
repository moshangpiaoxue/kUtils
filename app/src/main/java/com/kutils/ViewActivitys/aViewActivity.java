package com.kutils.ViewActivitys;

import com.kutils.ViewActivitys.TextViews.aTextViewActivity;
import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：view相关
 */
public class aViewActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("view相关");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list=new ArrayList<>();
        list.add(new MainBean("TextView", aTextViewActivity.class));
        list.add(new MainBean("XML背景", aTextViewActivity.class));
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
