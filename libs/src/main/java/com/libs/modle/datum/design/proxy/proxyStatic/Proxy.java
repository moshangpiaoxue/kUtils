package com.libs.modle.datum.design.proxy.proxyStatic;


/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能：代理对象
 */

public class Proxy implements IProxyView {
    private IProxyView iProxyView;

    public Proxy(IProxyView iProxyView) {
        this.iProxyView = iProxyView;
    }

    @Override
    public void buyBit() {
        iProxyView.buyBit();
    }

    @Override
    public void payMoney() {
        iProxyView.payMoney();
    }
}
