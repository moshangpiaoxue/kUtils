package com.kutils.UtilsActivtys;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kutils.R;
import com.kutils.modle.ListModle;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;

/**
 * @ author：mo
 * @ data：2019/9/9:9:24
 * @ 功能：键盘相关
 */
public class KeyBordActivity extends KBaseListActivity<String> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("键盘相关");
        kRecycleview.setLayoutGrid(4);
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<String> getAdapter() {
        return new KRecycleViewAdapter<String>(mActivity, ListModle.getList(10)) {
            @Override
            protected int getItemLayout(int position) {
                return R.layout.iten;
            }

            @Override
            public void doWhat(KRecycleViewHolder holder, final String bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                if (position==0){
                    item_1.setText("上下文打开键盘");
                }else if (position==1){
                    item_1.setText("上下文关闭键盘");
                }

            }
        };
    }
}