package com.libs.modle.datum.design.factory.factory.factory.impl;


import com.libs.modle.datum.design.factory.factory.factory.IMapFactory;
import com.libs.modle.datum.design.factory.factory.map.IMapView;
import com.libs.modle.datum.design.factory.factory.map.impl.BaiduMapView;

public class BaiduMapFactory implements IMapFactory {

	@Override
	public IMapView getMapView() {
		return new BaiduMapView();
	}

}
