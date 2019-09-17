package com.kutils.UtilsActivtys;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kutils.R;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.ResUtil;
import com.libs.utils.colorsUtils.ColorUtils;
import com.qmuiteam.qmui.util.QMUIColorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/9/2:15:12
 * @ 功能：颜色相关
 */
public class ColorsActivity extends KBaseListActivity<String> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("颜色相关");
        loadSuccess();
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<String> getAdapter() {
        return new KRecycleViewAdapter<String>(mActivity, getNoDatas()) {
            @Override
            protected int getItemLayout(int position) {
                return R.layout.iten;
            }

            @Override
            public void doWhat(KRecycleViewHolder holder, final String bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                item_1.setText(bean);
                if (position==0){
                    item_1.setBackgroundColor(QMUIColorHelper.setColorAlpha(ResUtil.getColor(R.color.text_red),0.5f));
                }else if (position == 1) {
                }
            }
        };
    }

    private List<String> getNoDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        return list;
    }
}