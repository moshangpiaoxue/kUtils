package com.libs.modle.datum.design.single;

/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能： 单例模式--懒汉式
 */

public class SingleLan {
    private static SingleLan instance;
    /**
     * 线程并发时，当这个变量被修改了，其他的并发线程会收到这个修改的通知 ，其他的线程就可以访问
     * volatile--他会屏蔽虚拟机的优化过程
     */
    private volatile static SingleLan instance3;

    public SingleLan() {
    }

    /**
     * 第一种用法：如果有现成的就用现成的，没有再新建，能懒就懒
     * 调用的时候先判断原来有没有用过，如果有说明这不是第一次调用就使用原来创建的，如果没有，说明这是第一次用，那就新建，
     * 优点：性能高.
     * 缺点：耗内存 存在隐患--并发时会出问题
     * 使用场景：一般情况下可以使用这个，在客户端，一般不会有并发的情况
     */
    public static SingleLan getInstance1() {
        if (instance == null) {
            instance = new SingleLan();
        }
        return instance;
    }

    /**
     * 第二种用法：加同步线程锁   **不推荐**
     * 双检索在java5之前是有问题的，但是java5在内存模型中有了volatile变量之后就没问题了
     * <p>
     * 当有并发线程同时执行的时候，由于加了线程锁，会让以后的线程等待，会阻塞线程，
     * 方法没有同步，调用效率高
     */
    public static synchronized SingleLan getInstance2() {
        if (instance == null) {
            instance = new SingleLan();
        }
        return instance;
    }

    /**
     * 第三种用法：双重检查 **推荐使用-既不浪费内存，性能相对也比较高**
     * iOS的通用写法
     */
    public static synchronized SingleLan getInstance3() {
        if (instance3 == null) {
            //给这个类加锁
            synchronized (SingleLan.class) {
                if (instance3 == null) {
                    instance3 = new SingleLan();
                }
            }
        }
        return instance3;
    }

}
