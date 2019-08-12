package com.libs.view.menu.SatelliteMenu;

/**
 * @ author：mo
 * @ data：2019/8/5:14:16
 * @ 功能：以给定的总度数线性分布卫星。
 */
public class LinearDegreeProvider implements IDegreeProvider {
    public float[] getDegrees(int count, float totalDegrees){
        if(count < 1){
            return new float[]{};
        }

        if(count == 1){
            return new float[]{45};
        }

        float[] result = null;
        int tmpCount = count-1;

        result = new float[count];
        float delta = totalDegrees / tmpCount;

        for(int index=0; index<count; index++){
            int tmpIndex = index;
            result[index] = tmpIndex * delta;
        }

        return result;
    }
}