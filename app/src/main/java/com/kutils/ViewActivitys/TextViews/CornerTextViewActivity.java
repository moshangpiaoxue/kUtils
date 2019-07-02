package com.kutils.ViewActivitys.TextViews;

import android.view.View;

import com.kutils.R;
import com.libs.ui.activity.KBaseLayoutActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：圆角TextView
 */
public class CornerTextViewActivity extends KBaseLayoutActivity {

    @Override
    protected int getMainLayoutId() {
        return R.layout.act_view_textview_corner;
    }


    @Override
    protected void getData() {

    }


    @Override
    protected void initViews(View mainView) {
        title.setMidleText("圆角TextView");
        loadSuccess();
    }
}
