package com.libs.utils.task.control;


/**
 * @description: 任务管理接口
 * @author: mo
 * @date: 2017/11/17 0017 17:20
*/
public interface KTaskController {

    /**
     * 在UI线程执行runnable.
     * 如果已在UI线程, 则直接执行.
     *
     * @param runnable
     */
    void autoPost(Runnable runnable);

    /**
     * 在UI线程执行runnable.
     * post到msg queue.
     *
     * @param runnable
     */
    void post(Runnable runnable);

    /**
     * 在UI线程执行runnable.
     *
     * @param runnable
     * @param delayMillis 延迟时间(单位毫秒)
     */
    void postDelayed(Runnable runnable, long delayMillis);

    /**
     * 在后台线程执行runnable
     *
     * @param runnable
     */
    void run(Runnable runnable);

    /**
     * 移除post或postDelayed提交的, 未执行的runnable
     *
     * @param runnable
     */
    void removeCallbacks(Runnable runnable);

}
