package com.kutils.UtilsActivtys;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kutils.R;
import com.libs.k;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.listener.clickListener.KOnItemClickListenerImpl;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.bengUtil.NextOtherActivityUtil;
import com.libs.utils.systemUtils.KNotificationUtil;
import com.mic.etoast2.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：吐司
 */
public class ToastUtilActivity extends KBaseListActivity<String> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("吐司");

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> list = new ArrayList<>();
        list.add("跳权限" + KNotificationUtil.isNotificationEnabled());
        list.add("吐司");
        refeshAdapter(list);
    }

    private void click(int position, String bean) {
        if (bean.contains("跳权限")) {
            NextOtherActivityUtil.toSettingNotification2(mActivity);
        } else if (bean.contains("吐司")) {
            Toast.makeText(k.app(), "vvvvvv", 1000).show();
//            ToastUtils.initStyle(new ToastWhiteStyle(){
//                @Override
//                public int getGravity() {
//                    return Gravity.BOTTOM;
//                }
//
//                @Override
//                public int getYOffset() {
//                    return 20;
//                }
//            });
//            ToastUtils.show("AAAAAAAAAAAAAAAA");
//            Toast.makeText(mActivity, "AAAAAAAAAAAAAAAA", 1000).show();
//            showToast("AAAAAAAAAAAAAAAA");
        }
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
                item_1.setText(bean);
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int position) {
                        super.onItemClick(view, position);
                        click(position, bean);
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
