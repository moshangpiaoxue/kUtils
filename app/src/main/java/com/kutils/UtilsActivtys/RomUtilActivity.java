package com.kutils.UtilsActivtys;

import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.systemUtils.RomUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：系统厂商信息
 */
public class RomUtilActivity extends KBaseListActivity<String> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("系统厂商信息");
        List<String> list = new ArrayList<>();
        list.add("系统名：" + RomUtil.getName());
        list.add("系统版本：" + RomUtil.getVersion());
        list.add("是否是华为：" + RomUtil.isEmui());
        list.add("是否是小米：" + RomUtil.isMiui());
        list.add("是否是OPPO：" + RomUtil.isOppo());
        list.add("是否是VIVO：" + RomUtil.isVivo());
        list.add("是否是魅族：" + RomUtil.isFlyme());
        list.add("是否是三星：" + RomUtil.isSmartisan());
        list.add("是否是360：" + RomUtil.is360());
        refeshAdapter(list);
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<String> getAdapter() {
        return AdapterModle.getAdapter(mActivity, mData);
    }

}
