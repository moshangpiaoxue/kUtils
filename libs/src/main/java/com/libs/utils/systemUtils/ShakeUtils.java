package com.libs.utils.systemUtils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 摇一摇工具类
 * 使用说明：
 * private ShakeUtils mShakeUtils = null;
 * 1、在需要使用摇一摇功能的Activity实例化该工具类并设置摇一摇监听：
 * mShakeUtils = new ShakeUtils( this );
 * mShakeUtils.setOnShakeListener(new OnShakeListener{
 * 		public void onShake(){
 * 			// 此处为摇一摇触发后的操作
 * 		}
 * });
 *
 * 2、分别在Activity的onResume和onPause方法中调用该工具类的onResume和onPause方法：
 * mShakeUtils.onResume();
 * mShakeUtils.onPause();
 * */
public class ShakeUtils implements SensorEventListener {
	public ShakeUtils(Context context){
			mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	public void setOnShakeListener( OnShakeListener onShakeListener ){
		mOnShakeListener = onShakeListener;
	}

	public void onResume(){
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onPause(){
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		//values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER){
			//这里可以调节摇一摇的灵敏度
			if ((Math.abs(values[0]) > SENSOR_VALUE || Math.abs(values[1]) > SENSOR_VALUE || Math.abs(values[2]) > SENSOR_VALUE)){
				System.out.println("sensor value == " + " " + values[ 0 ] + " " + values[ 1 ] + " " +  values[ 2 ] );
				if( null != mOnShakeListener ){
					mOnShakeListener.onShake( );
				}
			}
		}
	}

	public interface OnShakeListener{
		public void onShake();
	}

	private SensorManager mSensorManager = null;
	private OnShakeListener mOnShakeListener = null;
	private static final double SENSOR_VALUE = 15;
}
