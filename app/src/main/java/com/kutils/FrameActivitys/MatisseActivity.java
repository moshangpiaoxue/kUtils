package com.kutils.FrameActivitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kutils.R;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.constants.ConstansePermissionGroup;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.utils.image.BitmapUtil;
import com.libs.utils.image.DrawableUtil;
import com.libs.view.recyclerView.KRecycleView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/9/4:14:10
 * @ 功能：知乎图片选择框架
 */
public class MatisseActivity extends KBaseActivity {
    private KRecycleView rv_matisse;
    List<Uri> mData = new ArrayList<>();
    KRecycleViewAdapter<Uri> adapter;
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected int getLayoutId() {
        return R.layout.act_matisse;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        rv_matisse = findViewById(R.id.rv_matisse);
        adapter = new KRecycleViewAdapter<Uri>(mActivity, mData) {
            @Override
            protected int getItemLayout(int position) {
                return R.layout.iten;
            }

            @Override
            public void doWhat(KRecycleViewHolder holder, final Uri bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                item_1.setText(position+"");
                item_1.setBackground(DrawableUtil.getDrawable(BitmapUtil.getBitmap(bean)));
            }
        };
        rv_matisse.setAdapter(adapter);

        btn_matisse = findViewById(R.id.btn_matisse);
        btn_matisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(mActivity)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(5)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.kutils.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//

            }
        });
        requestPermission(ConstansePermissionGroup.PERMISSIONS_CAMERA);
        requestPermission(ConstansePermissionGroup.PERMISSIONS_STORAGE);
    }

    Button btn_matisse;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mData = dataMatisse.obtainResult(data);
            adapter.refreshView(Matisse.obtainResult(data));
//            btn_matisse.setText(result.toString());
        }
    }

}
