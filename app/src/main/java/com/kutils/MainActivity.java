package com.kutils;

import android.graphics.drawable.GradientDrawable;

import com.kutils.FrameActivitys.aFrameActivity;
import com.kutils.UtilsActivtys.SettingWhiteListActivity;
import com.kutils.UtilsActivtys.aUtilActivity;
import com.kutils.ViewActivitys.aViewActivity;
import com.kutils.algorithmActivitys.AlgorithmActivity;
import com.kutils.algorithmActivitys.LintCodeActivity;
import com.kutils.bean.MainBean;
import com.kutils.modle.AdapterModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.ResUtil;
import com.libs.utils.appUtils.barUtils.SratusBarUtil;
import com.libs.utils.colorsUtils.ColorUtils;
import com.libs.utils.zBuilder.ShapeBuilder;

import java.util.ArrayList;
import java.util.List;

import mo.lib.rxjava.bus.RxBus;


public class MainActivity extends KBaseListActivity<MainBean> {

    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        other();
        String x = "goeasyway";
        change(x);
        System.out.println(x);
    }

    public void change(String x) {
        x = "even";
    }

    int tag = 0;

    private void other() {
        SratusBarUtil.setStatusBar(mActivity, ResUtil.getDrawable(R.mipmap.aa));
        title.setMidleText("首页");
        kRecycleview.setLayoutGrid(4);
        actionGpsListener();
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("View相关", aViewActivity.class));
        list.add(new MainBean("Utils相关", aUtilActivity.class));
        list.add(new MainBean("算法相关", AlgorithmActivity.class));
        list.add(new MainBean("刷题", LintCodeActivity.class));
        list.add(new MainBean("测试白名单", SettingWhiteListActivity.class));
        list.add(new MainBean("测试框架", aFrameActivity.class));
        refeshAdapter(list);
        SratusBarUtil.setStatusBar(mActivity, ColorUtils.getColor(ResUtil.getColor(R.color.color_f75137),0.5F,true));

    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.getInstance().post("AAAAAAAAAA");
    }

    @Override
    protected void getList(int page) {

        ShapeBuilder shapeBuilder = new ShapeBuilder();
        GradientDrawable drawable = shapeBuilder.setColor(R.color.color_000000).setShape(GradientDrawable.RING).getShape();

    }


    @Override
    protected KRecycleViewAdapter<MainBean> getAdapter() {
        return AdapterModle.getMainAdapter(mActivity, mData);
    }

}
