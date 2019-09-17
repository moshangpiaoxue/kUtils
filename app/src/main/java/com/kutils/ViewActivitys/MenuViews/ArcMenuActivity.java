package com.kutils.ViewActivitys.MenuViews;

import android.view.View;

import com.kutils.R;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.view.menu.KArcMenu;

/**
 * @ author：mo
 * @ data：2019/7/29:18:18
 * @ 功能： 卫星菜单=分四个方向单向弹出
 */
public class ArcMenuActivity extends KBaseActivity {
            private static final int[] ITEM_DRAWABLES =
        { R.drawable.composer_camera, R.drawable.composer_music,  R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought,
        R.drawable.composer_with };
//    private static final int[] ITEM_DRAWABLES = {R.drawable.composer_camera, R.drawable.composer_music};

    @Override
    protected int getLayoutId() {
        return R.layout.act_menu_arcmenu;
    }


    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        init1();

    }

    private void init1() {
        KArcMenu kam_satellite_1 = findViewById(R.id.kam_satellite_1);
        kam_satellite_1.setRadius(60)
                .setPosition(KArcMenu.Position.LEFT_BOTTOM)
                .addItemViews(ITEM_DRAWABLES);


        KArcMenu kam_arc = findViewById(R.id.kam_arc);
        kam_arc.setRadius(50).setPosition(KArcMenu.Position.LEFT_TOP).addItemViews(ITEM_DRAWABLES);
    }


}
