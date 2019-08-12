package com.libs.view.menu.SatelliteMenu;

/**
 * @ author：mo
 * @ data：2019/8/5:14:15
 * @ 功能：卫星间度数的默认提供程序。最多3颗卫星
 * 试图使卫星在给定的总度数内居中。对于等于和的数字
 * 大于4，使用最小和最大度数均匀地蒸馏。
 */
public class DefaultDegreeProvider implements IDegreeProvider {
    public float[] getDegrees(int count, float totalDegrees){
        if(count < 1)
        {
            return new float[]{};
        }

        float[] result = null;
        int tmpCount = 0;
        if(count < 4){
            tmpCount = count+1;
        }else{
            tmpCount = count-1;
        }

        result = new float[count];
        float delta = totalDegrees / tmpCount;

        for(int index=0; index<count; index++){
            int tmpIndex = index;
            if(count < 4){
                tmpIndex = tmpIndex+1;
            }
            result[index] = tmpIndex * delta;
        }

        return result;
    }
}