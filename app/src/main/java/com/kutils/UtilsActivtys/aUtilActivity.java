package com.kutils.UtilsActivtys;

import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：Utils相关
 */
public class aUtilActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("Utils相关");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("SpannableStringUtil", StringUtilsActivity.class));
        list.add(new MainBean("跳系统界面", BengSysActivity.class));
        list.add(new MainBean("吐司", ToastUtilActivity.class));
        list.add(new MainBean("系统厂商信息", RomUtilActivity.class));
        list.add(new MainBean("应用分身检查", AppSeparationActivity.class));
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
