package com.libs.modle.datum.design.single;

/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：单例模式--静态内部类 饿汉式的升级版
 * 用的时候创建，不用的时候不创建
 * 使用场景：要求要懒加载（lazy initialization）
 */

public class SingleStatic {
    private static class SingleStaticHolder{
        private static SingleStatic instance=new SingleStatic();
    }
    public static SingleStatic getInstance(){
        return SingleStaticHolder.instance;
    }
}
