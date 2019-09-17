package com.kutils.ViewActivitys.MenuViews;

import android.util.Log;
import android.view.View;

import com.kutils.R;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.view.menu.SatelliteMenu.SatelliteMenu;
import com.libs.view.menu.SatelliteMenu.SatelliteMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2019/8/5:14:09
 * @ 功能：卫星菜单
 */
public class SatelliteMenuActivity extends KBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.act_view_menu_satellite;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {

        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);

//		  Set from XML, possible to programmatically set
//        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
//        menu.setSatelliteDistance((int) distance);
//        menu.setExpandDuration(500);
//        menu.setCloseItemsOnClick(false);
//        menu.setTotalSpacingDegree(60);

        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.mipmap.aa));
        items.add(new SatelliteMenuItem(4, R.mipmap.aa));
        items.add(new SatelliteMenuItem(4, R.mipmap.aa));
        items.add(new SatelliteMenuItem(3, R.mipmap.aa));
        items.add(new SatelliteMenuItem(2, R.mipmap.aa));
        items.add(new SatelliteMenuItem(1, R.mipmap.aa));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);

        menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);
            }
        });
    }
}
