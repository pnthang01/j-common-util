package com.etybeno.common.service;



import com.etybeno.common.service.model.ExecutorCleanupUnit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangpham on 20/11/2017.
 */
public class ThreadPoolService {

    private static ThreadPoolService _instance;
    private ScheduledExecutorService threadPool;

    public ThreadPoolService() {
        threadPool = Executors.newScheduledThreadPool(20);
        ShutdownHookCleanup shutdownHook = ShutdownHookCleanup.load();
        shutdownHook.addExecutor(new ExecutorCleanupUnit("ThreadPoolService", threadPool));
    }

    public synchronized static ThreadPoolService load() {
        if (null == _instance) _instance = new ThreadPoolService();
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
