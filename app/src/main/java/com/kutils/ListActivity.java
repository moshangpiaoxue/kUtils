package com.kutils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.task.handlers.HandlerUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/3/27:19:04
 * @ 功能：
 */
public class ListActivity extends KBaseListActivity<String> {

    @Override
    protected void initListView() {
        kPullLayout.setCanRefresh(true);
        kPullLayout.setCanLoadMore(true);
    }

    @Override
    protected void getMore(int page) {
        HandlerUtil.postRunnable(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                list.add("c");
                list.add("d");
                list.add("e");
                list.add("f");
                list.add("g");
                loardMoreAdapter(list);
            }
        }, 1000);
    }

    @Override
    protected void getList(int page) {
        HandlerUtil.postRunnable(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                list.add("1");
                refeshAdapter(list);
            }
        }, 1000);
    }

    @Override
    protected KRecycleViewAdapter<String> getAdapter() {
        return new KRecycleViewAdapter<String>(mActivity, mData) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView parent) {
                TextView textView = holder.getView(R.id.item_1);
                textView.setText(bean);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refeshAdapter(null);
                    }
                });
            }

            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.iten;
            }
        };
    }

}
