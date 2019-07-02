package com.libs.modle.datum.design.factory.factory.factory.impl;


import com.libs.modle.datum.design.factory.factory.factory.AbsMapFactory;
import com.libs.modle.datum.design.factory.factory.map.IMapView;

public class DefaultMapFactory extends AbsMapFactory {
	
	private static DefaultMapFactory defaultMapFactory;
	
	private DefaultMapFactory() {
	}
	
	public static DefaultMapFactory getInstance(){
		if(defaultMapFactory == null){
			defaultMapFactory = new DefaultMapFactory();
		}
		return defaultMapFactory;
	}

	@Override
	public <T extends IMapView> T getMapView(Class<T> clzz) {
		try {
			// 反射
			return clzz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
