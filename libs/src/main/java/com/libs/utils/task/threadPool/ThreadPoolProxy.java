package com.libs.utils.task.threadPool;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description: 线程池代理类
 * autour: mo
 * date: 2017/9/8 0008 20:42
*/
public class ThreadPoolProxy {
    ThreadPoolExecutor mThreadPoolExecutor;

    private int corePoolSize; //核心线程数
    private int maximumPoolSize;//最大长度
    private long keepAliveTime;//存活时间-非核心线程时超过时长

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    private ThreadPoolExecutor initExecutor() {
        if(mThreadPoolExecutor == null) {
            synchronized(ThreadPoolProxy.class) {
                if(mThreadPoolExecutor == null) {

                    TimeUnit unit =  TimeUnit.MILLISECONDS;
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
                    LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

                    mThreadPoolExecutor = new ThreadPoolExecutor(
                            corePoolSize,//核心线程数
                            maximumPoolSize,//最大线程数
                            keepAliveTime,//保持时间
                            unit,//保持时间对应的单位
                            workQueue,
                            threadFactory,//线程工厂
                            handler);//异常捕获器
                }
            }
        }
        return mThreadPoolExecutor;
    }



    /**执行任务*/
    public void executeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.execute(r);
    }


    /**提交任务*/
    public Future<?> commitTask(Runnable r) {
        initExecutor();
        return mThreadPoolExecutor.submit(r);
    }

    /**删除任务*/
    public void removeTask(Runnable r) {
        initExecutor();
        mThreadPoolExecutor.remove(r);
    }

}