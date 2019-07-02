package com.libs.modle.datum.design.factory.factory.map.impl;


import android.view.View;

import com.libs.modle.datum.design.factory.factory.map.IMapView;


public class BaiduMapView implements IMapView {

	@Override
	public View getView() {
		System.out.println("调用了百度地图的getView");
		return null;
	}

	@Override
	public void setMapType(MapType mapType) {
		System.out.println("调用了百度地图的setMapType");
	}

}
