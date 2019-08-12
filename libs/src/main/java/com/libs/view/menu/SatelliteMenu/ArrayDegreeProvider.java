package com.libs.view.menu.SatelliteMenu;

/**
 * @ author：mo
 * @ data：2019/8/5:14:13
 * @ 功能：提供每个卫星之间的度数作为度数数组。可提供给
 * @link satellitemenu作为参数。
 */
public class ArrayDegreeProvider implements IDegreeProvider {
    private float[] degrees;

    public ArrayDegreeProvider(float[] degrees) {
        this.degrees = degrees;
    }

    public float[] getDegrees(int count, float totalDegrees){
        if(degrees == null || degrees.length != count){
            throw new IllegalArgumentException("Provided delta degrees and the action count are not the same.");
        }
        return degrees;
    }
}