package com.kutils.ViewActivitys.RecyclerViews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kutils.R;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.ryan.rv_gallery.AnimManager;
import com.ryan.rv_gallery.GalleryRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/7/11:9:52
 * @ 功能：画廊效果
 */
public class GalleryRecyclerViewActivity extends KBaseActivity {
    private GalleryRecyclerView grv_view_hor;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.act_galleryrecyclerview;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        list.add(R.mipmap.aa);
        list.add(R.mipmap.aa);
        list.add(R.mipmap.aa);
        list.add(R.mipmap.aa);
        grv_view_hor = findViewById(R.id.grv_view_hor);
        grv_view_hor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        grv_view_hor.setAdapter(new KRecycleViewAdapter<Integer>(mActivity, list) {
            @Override
            public void doWhat(KRecycleViewHolder holder, Integer bean, int position, int itemViewType, RecyclerView parent) {
                ImageView iv_item_image = holder.getView(R.id.iv_item_image);
                iv_item_image.setBackgroundResource(bean);
            }

            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.item_image;
            }
        });
        grv_view_hor
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(new GalleryRecyclerView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        showToast("点击了" + i);
                    }
                })
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(0)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();
    }
}
