package com.kutils.algorithmActivitys;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kutils.R;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.datum.algorithm.SearchUtils;
import com.libs.modle.datum.algorithm.SortUtils;
import com.libs.modle.listener.clickListener.KOnItemClickListenerImpl;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.ui.activity.KBaseListActivity;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.viewUtil.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/7/3:14:11
 * @ 功能：算法相关
 */
public class AlgorithmActivity extends KBaseListActivity<String> {
    private List<Integer> def = new ArrayList<Integer>();
    List<String> list = new ArrayList<>();
    //查数据值
    EditText tv_alg_data_find_type;

    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("算法相关");
        addtitle();
    }


    @Override
    protected void onResume() {
        super.onResume();


        list.add("查找-二分查找-递归");
        list.add("查找-二分查找-while");
        list.add("查找-二分查找-while-第一次出现");
        int[] aa = new int[]{1, 2, 3, 6, 5, 4};
        list.add("排序-冒泡数组升序：" + SortUtils.getList(aa) + "==" + SortUtils.SortBubble(aa, true));
        list.add("排序-冒泡数组降序：" + SortUtils.getList(aa) + "==" + SortUtils.SortBubble(aa, false));
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(3);
        integers.add(5);
        integers.add(6);
        integers.add(4);
        integers.add(2);
        list.add("排序-list升序：" + integers.toString() + "==" + SortUtils.Sort(integers, true));
        list.add("排序-list降序：" + integers.toString() + "==" + SortUtils.Sort(integers, false));

        list.add("排序-数组转list升序：" + SortUtils.getList(aa) + "==" + SortUtils.Sort(aa, true));
        list.add("排序-数组转list降序：" + SortUtils.getList(aa) + "==" + SortUtils.Sort(aa, false));
        refeshAdapter(list);
    }


    private void click(String bean, int position) {
        if (bean.equals("查找-二分查找-递归")) {
            int posstion111 = SearchUtils.searchBinary(def, 0, def.size(), Integer.parseInt(tv_alg_data_find_type.getText().toString()));
            title.setMidleText(posstion111 + "");
        } else if (bean.equals("查找-二分查找-while")) {
            title.setMidleText(SearchUtils.searchBinary(def, Integer.parseInt(tv_alg_data_find_type.getText().toString())) + "");
        } else if (bean.equals("查找-二分查找-while-第一次出现")) {
            title.setMidleText(SearchUtils.searchBinaryFirstEqual(def, Integer.parseInt(tv_alg_data_find_type.getText().toString())) + "");
        } else if (bean.equals("排序-冒泡")) {

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
                item_1.setGravity(Gravity.LEFT);
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int position) {
                        super.onItemClick(view, position);
                        click(bean, position);
                    }
                });

            }

            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.iten;
            }
        };
    }


    private void addtitle() {
        View tt = ViewUtil.getView(mActivity, R.layout.algorithm_top, listAddlTop);
        //默认数据展示
        final TextView tv_alg_data = tt.findViewById(R.id.tv_alg_data);
        //自定义数据
        final EditText tv_alg_data_cum = tt.findViewById(R.id.tv_alg_data_cum);
        //默认初始化数据
        tt.findViewById(R.id.tv_alg_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.clear();
                def.add(1);
                def.add(2);
                def.add(3);
                def.add(4);
                def.add(5);
                def.add(6);
                def.add(7);
                def.add(8);
                tv_alg_data.setText(def.toString());

            }
        });
        //添加
        Button tv_alg_add = tt.findViewById(R.id.tv_alg_add);
        tv_alg_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = getCumDate(tv_alg_data_cum.getText().toString());
                if (val != -1) {
                    def.add(val);
                }
                tv_alg_data_cum.setText("");
                tv_alg_data.setText(def.toString());
            }
        });
        //删除
        tt.findViewById(R.id.tv_alg_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (def.size() != 0) {
                    def.remove(def.size() - 1);
                    tv_alg_data.setText(def.toString());
                }
            }
        });
        //清空
        tt.findViewById(R.id.tv_alg_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def.clear();
                tv_alg_data.setText(def.toString());
            }
        });

        tv_alg_data_find_type = tt.findViewById(R.id.tv_alg_data_find_type);

        listAddlTop.addView(tt);
    }

    private int getCumDate(String toString) {
        int aa = 0;
        if (!StringUtil.isEmpty(toString)) {
            try {
                aa = Integer.parseInt(toString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aa;
    }

}