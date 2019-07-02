package com.libs.modle.datum.design.factory.factorySimple;

/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：简单工厂模式--接口
 */

public interface KView {
    /**
    * 谁回答
    */
     enum Who{
        zhang,li;
    }

    /**
     * 回答什么
     * @param aaa
     */
    public void answer(String aaa);

}
