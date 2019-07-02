package com.libs.modle.datum.design.proxy.proxyStatic.impl;


import com.libs.modle.datum.design.proxy.proxyStatic.IProxyView;

/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能：谁要干
 */

public class Zhang implements IProxyView {
    @Override
    public void buyBit() {
        System.out.print("张三要买币");
    }

    @Override
    public void payMoney() {
        System.out.print("张三要花钱");
    }
}
