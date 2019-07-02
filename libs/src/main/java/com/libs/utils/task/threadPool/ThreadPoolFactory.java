package com.libs.utils.task.threadPool;


/**
 * description: 线程池工厂类
 * autour: mo
 * date: 2017/9/8 0008 20:45
 */
public enum ThreadPoolFactory {
    /**
     * 枚举单例
     */
    INSTANCE;

    private static ThreadPoolProxy commonThreadPool;
    //核心线程数
    public static final int Common_CORE_POOL_SIZE = 5;
    //最大长度
    public static final int Common_MAX_POOL_SIZE = 5;
    //存活时间 -非核心线程时超过时长
    public static final int Common_KEEP_LIVE_TIME = 1;

    public ThreadPoolProxy getThreadPool() {
        return commonThreadPool = new ThreadPoolProxy(Common_CORE_POOL_SIZE,
                Common_MAX_POOL_SIZE, Common_KEEP_LIVE_TIME);
    }

//    //单例模式
//    public static ThreadPoolProxy getCommonThreadPool() {
//        if (commonThreadPool == null) {
//            synchronized (ThreadPoolFactory.class) {
//                if (commonThreadPool == null) {
//                    commonThreadPool = new ThreadPoolProxy(Common_CORE_POOL_SIZE, Common_MAX_POOL_SIZE, Common_KEEP_LIVE_TIME);
//                }
//            }
//        }
//        return commonThreadPool; //返回所要的线程池
//    }
}