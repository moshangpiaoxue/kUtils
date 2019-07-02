package com.libs.modle.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：方向传感器
 */
public class KOrientationSensor {
    /**
     * 获取方向传感器
     *
     * @return
     */
    public static Sensor getOrientationSensor() {
        return KSensorManager.getSensorManager().getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    /**
     * 注册监听
     *
     * @param listener
     * @return
     */
    public static SensorManager registListener(SensorEventListener listener) {
        SensorManager manager = KSensorManager.getSensorManager();
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        return manager;
    }

    public static SensorManager registListener() {
        SensorManager manager = KSensorManager.getSensorManager();
        manager.registerListener(new KSensorEventListener() {
            /**
             * event.values[0]：方位角 手机绕着Z轴旋转的角度。0表示正北(North)，90表示正东(East)，180表示正南(South)，270表示正西(West)。
             * event.values[1]：倾斜角 手机翘起来的程度，当手机绕着x轴倾斜时该值会发生变化。取值范围是[-180,180]之间。
             * event.values[2]：滚动角 沿着Y轴的滚动角度，取值范围为：[-90,90]
             * @param event
             */
            @Override
            public void onSensorChanged(SensorEvent event) {
                super.onSensorChanged(event);
//                (float) (Math.round(event.values[0] * 100)) / 100
            }
        }, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        return manager;
    }

    /**
     * 取消注册
     *
     * @param manager
     * @param listener
     */
    public static void unregisterListener(SensorManager manager, SensorEventListener listener) {
        if (manager != null) {
            manager.unregisterListener(listener);
        }
    }

    public static String getOrientation(float result) {
        if (result >360||result<5) {
            return "正北" + result;
        } else  if (result >85&&result<95) {
            return "正东" + result;
        }else  if (result >175&&result<185) {
            return "正南" + result;
        }else  if (result >265&&result<275) {
            return "正西" + result;
        } else {
            return result + "";
        }
    }
}
