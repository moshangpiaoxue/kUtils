package com.libs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ author：mo
 * @ data：2019/8/27:14:12
 * @ 功能：
 */
public class Test {
    public Test() {
        init();
    }

    private void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                }
            };
            //Thread.sleep(1000);
            executorService.execute(thread);
        }
        //关闭线程池
        executorService.shutdown();
    }
}
