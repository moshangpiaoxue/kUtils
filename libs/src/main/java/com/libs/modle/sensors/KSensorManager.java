package com.libs.modle.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.libs.k;

import java.util.List;



/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：传感器管理器
 */
public class KSensorManager {
    /**
     * 获取传感器管理器
     */
    public static SensorManager getSensorManager() {
        return (SensorManager) k.app().getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * 获取传感器信息
     *
     * @param sensor
     * @return
     */
    public static String getSensorInfo(Sensor sensor) {
        StringBuilder builder = new StringBuilder();
        builder.append("传感器名称=" + sensor.getName() + "\n");
        builder.append("传感器种类=" + sensor.getType() + "\n");
        builder.append("传感器供应商=" + sensor.getVendor() + "\n");
        builder.append("传感器版本=" + sensor.getVersion() + "\n");
        builder.append("传感器精度值=" + sensor.getResolution() + "\n");
        builder.append("传感器最大范围=" + sensor.getMaximumRange() + "\n");
        builder.append("传感器耗电量=" + sensor.getPower() + "\n");
        return builder.toString();
    }

    /**
     * 获取当前设备支持的所有传感器
     *
     * @return
     */
    public static List<Sensor> getAllSensors() {
        return getSensorManager().getSensorList(Sensor.TYPE_ALL);
    }

    /**
     * 获取当前设备支持的所有传感器
     *
     * @return
     */
    public static String getAllSensor() {
        List<Sensor> sensors = getAllSensors();
        StringBuilder sb = new StringBuilder();
        sb.append("当前设备支持传感器数：" + sensors.size() + "   分别是：\n\n");
        for (Sensor s : sensors) {
            switch (s.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append("加速度传感器(Accelerometer sensor)" + "\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append("陀螺仪传感器(Gyroscope sensor)" + "\n");
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append("光线传感器(Light sensor)" + "\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append("磁场传感器(Magnetic field sensor)" + "\n");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append("方向传感器(Orientation sensor)" + "\n");
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append("气压传感器(Pressure sensor)" + "\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    sb.append("距离传感器(Proximity sensor)" + "\n");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append("温度传感器(Temperature sensor)" + "\n");
                    break;
                default:
                    sb.append("其他传感器" + "\n");
                    break;
            }
            sb.append(getSensorInfo(s) + "\n\n");
        }

        return sb.toString();
    }
}
