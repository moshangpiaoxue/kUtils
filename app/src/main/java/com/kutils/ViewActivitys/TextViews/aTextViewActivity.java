package com.kutils.ViewActivitys.TextViews;

import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：TextView相关
 */
public class aTextViewActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("TextView相关");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list=new ArrayList<>();
        list.add(new MainBean("旋转TextView", SlantedTextViewActivity.class));
        list.add(new MainBean("圆角TextView", CornerTextViewActivity.class));
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
        return AdapterModle.getMainAdapter(mActivity, new ArrayList<MainBean>());
    }

}
