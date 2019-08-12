package com.libs.view.menu.SatelliteMenu;

/**
 * @ author：mo
 * @ data：2019/8/5:14:14
 * @ 功能：提供卫星间度数的接口。
 */
public interface IDegreeProvider {
    public float[] getDegrees(int count, float totalDegrees);
}