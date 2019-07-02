package com.libs.modle.datum.design.factory.factory.map;


import android.view.View;

/**
 * 地图规范
 * @author Dream
 *
 */
public interface IMapView {
	enum MapType {
		// 空白背景模式常量
		MAP_TYPE_NONE,
		// 普通地图模式常量
		MAP_TYPE_NORMAL,
		// 卫星图模式常量
		MAP_TYPE_SATELLITE
	}

	public View getView();

	public void setMapType(MapType mapType);
}
