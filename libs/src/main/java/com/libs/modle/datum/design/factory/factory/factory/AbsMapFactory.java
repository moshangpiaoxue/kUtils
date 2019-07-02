package com.libs.modle.datum.design.factory.factory.factory;


import com.libs.modle.datum.design.factory.factory.map.IMapView;

public abstract class AbsMapFactory {
	
	public abstract <T extends IMapView> T getMapView(Class<T> clzz);

	// 场景：场景一个对象，这个对象有很多默认的参数
}
