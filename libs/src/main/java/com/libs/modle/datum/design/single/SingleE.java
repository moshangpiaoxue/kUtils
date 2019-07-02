package com.libs.modle.datum.design.single;

/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：单例模式--饿汉式
 * 缺点：不能传参
 */

public class SingleE {
    //类初始化时，立即加载对象。天然的线程安全的
    public static final SingleE instance = new SingleE();

    public SingleE() {
    }

    /**
     * 说明：不管用不用，都提前新建一个准备好
     * 优点：安全（并发不会出问题） 方法没有同步，调用效率高
     * 缺点：耗内存（静态变量生命周期的缘故，就是这种写法，只要程序跑起来，这个类就回创建一个静态变量等待调用，他会一直在内存里，不管你用不用）
     */
    public static SingleE getInstance() {
        return instance;
    }
}
