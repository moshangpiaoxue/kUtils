package com.libs.modle.datum.design.proxy.proxyStatic;


import com.libs.modle.datum.design.proxy.proxyStatic.impl.Li;

/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能：
 */

public class TestProxy {
    public static void main(String[] args) {
        //被代理对象
        Li li = new Li();
        //代理对象
        Proxy proxy = new Proxy(li);
        //干什么
        proxy.buyBit();
        proxy.payMoney();

    }
}
