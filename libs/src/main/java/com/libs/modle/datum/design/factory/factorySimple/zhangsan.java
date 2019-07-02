package com.libs.modle.datum.design.factory.factorySimple;


/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：
 */

public class zhangsan implements KView {
    @Override
    public void answer(String aaa) {
        System.err.print("张三"+aaa);
    }
}
