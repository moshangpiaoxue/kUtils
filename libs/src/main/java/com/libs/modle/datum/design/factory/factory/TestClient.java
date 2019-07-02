package com.libs.modle.datum.design.factory.factory;


import com.libs.modle.datum.design.factory.factory.factory.AbsMapFactory;
import com.libs.modle.datum.design.factory.factory.factory.IMapFactory;
import com.libs.modle.datum.design.factory.factory.factory.impl.DefaultMapFactory;
import com.libs.modle.datum.design.factory.factory.factory.impl.GaodeMapFactory;
import com.libs.modle.datum.design.factory.factory.map.IMapView;
import com.libs.modle.datum.design.factory.factory.map.impl.GaodeMapView;

/**
 * 客户端－－指的是使用该工厂的实例对象
 * @author Dream
 *
 */
public class TestClient {
	
	public static void main(String[] args) {
		//场景，例如：百度地图里面他有全景图(百度地图特有模块，然后其他的地图不存在这个特殊模块)
		
		
		
		//工厂方法模式
		IMapFactory mapFactory = new GaodeMapFactory();
		mapFactory.getMapView().setMapType(IMapView.MapType.MAP_TYPE_NONE);
		
		AbsMapFactory factory = DefaultMapFactory.getInstance();
		GaodeMapView mapView = factory.getMapView(GaodeMapView.class);
		mapView.getView();

	}
	
}
