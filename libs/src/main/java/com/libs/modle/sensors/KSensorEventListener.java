package com.libs.modle.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：传感器监听
 */
public class KSensorEventListener implements SensorEventListener {
    /**
     * 当传感器发生变化
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    /**
     * 当准确度发生改变
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
