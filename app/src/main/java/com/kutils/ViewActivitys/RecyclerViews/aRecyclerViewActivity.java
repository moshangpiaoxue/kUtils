package com.kutils.ViewActivitys.RecyclerViews;

import com.kutils.ViewActivitys.TextViews.CornerTextViewActivity;
import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：RecyclerView相关
 */
public class aRecyclerViewActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("RecyclerView相关");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("画廊效果", GalleryRecyclerViewActivity.class));
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
