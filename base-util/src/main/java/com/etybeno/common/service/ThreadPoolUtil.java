package com.etybeno.common.service;


import com.etybeno.common.service.model.ExecutorCleanupUnit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangpham on 20/11/2017.
 */
public class ThreadPoolUtil {
    private static ThreadPoolUtil _instance;
    private ScheduledExecutorService threadPool;

    public ThreadPoolUtil() {
        threadPool = Executors.newScheduledThreadPool(20);
        ShutdownHookCleanup shutdownHook = ShutdownHookCleanup.load();
        shutdownHook.addExecutor(new ExecutorCleanupUnit("ThreadPoolUtil", threadPool));
    }

    public synchronized static ThreadPoolUtil load() {
        if (null == _instance) _instance = new ThreadPoolUtil();
        return _instance;
    }

    public static void shutdown() {
        if (null != _instance) {
            _instance.threadPool.shutdown();
        }
    }

    public void addThread(Runnable runnable, long delay, long period, TimeUnit tu) {
        if (period < 0) {
            threadPool.schedule(runnable, delay, tu);
        } else {
            threadPool.scheduleWithFixedDelay(runnable, delay, delay, tu);
        }
    }
}
