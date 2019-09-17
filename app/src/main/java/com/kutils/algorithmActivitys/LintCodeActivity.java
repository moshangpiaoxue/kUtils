package com.kutils.algorithmActivitys;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;

import com.kutils.R;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.datum.algorithm.LintCodeUtils;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/7/3:14:11
 * @ 功能：LintCode 刷题
 */
public class LintCodeActivity extends KBaseListActivity<String> {
    List<String> list = new ArrayList<>();

    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("算法相关");
    }


    @Override
    protected void onResume() {
        super.onResume();
        list.add("37:反转一个3位整数:123==" + LintCodeUtils.reverseInteger37(123));
        list.add("145:将一个字符由小写字母转换为大写字母:abc==" + LintCodeUtils.lowercaseToUppercase("abc"));
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
        return new KRecycleViewAdapter<String>(mActivity, mData) {
            @Override
            public void doWhat(KRecycleViewHolder holder, final String bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                item_1.setGravity(Gravity.LEFT);
                item_1.setText(bean);

            }

            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.iten;
            }
        };
    }


}