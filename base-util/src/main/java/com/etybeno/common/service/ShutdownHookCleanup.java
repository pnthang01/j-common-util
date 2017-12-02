package com.etybeno.common.service;

import com.etybeno.common.service.model.ExecutorCleanupUnit;
import com.etybeno.common.service.model.IService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangpham on 20/11/2017.
 */
public class ShutdownHookCleanup {

    static final Logger LOGGER = LogManager.getLogger(ShutdownHookCleanup.class);
    private final Deque<ExecutorCleanupUnit> cleanUpExecutor = new ArrayDeque<>();
    private final Deque<IService> cleanupService = new ArrayDeque();
    private static ShutdownHookCleanup _instance;

    public void addExecutor(ExecutorCleanupUnit executor) {
        cleanUpExecutor.add(executor);
    }

    public void addService(IService service) {
        cleanupService.add(service);
    }

    private ShutdownHookCleanup() {
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new ShutdownHook());
        LOGGER.info("Initialize shutdown hook cleanup");
    }

    public synchronized static ShutdownHookCleanup load() {
        if (null == _instance) _instance = new ShutdownHookCleanup();
        return _instance;
    }

    public class ShutdownHook extends Thread {

        @Override
        public void run() {
            while (!cleanupService.isEmpty()) {
                try {
                    IService poll = cleanupService.poll();
                    poll.shutdown();
                } catch (Exception ex) {
                    LOGGER.error("Failed to shutdown executor:", ex);
                }
            }
            //
            while (!cleanUpExecutor.isEmpty()) {
                try {
                    ExecutorCleanupUnit unit = cleanUpExecutor.poll();
                    LOGGER.info("Start to shuting down executor: " + unit.getName());
                    if (unit.getWaitOnShutdown() > 0) {
                        Thread.sleep(unit.getWaitOnShutdown());
                    }
                    LOGGER.info("Start to shuwdown executor: " + unit.getName());
                    unit.getExecutor().shutdown();
                    while (!unit.getExecutor().awaitTermination(3, TimeUnit.SECONDS)) {
                    }
                    LOGGER.info("Shutdown the executor successfully.");
                } catch (Exception ex) {
                    LOGGER.error("Failed to shutdown executor:", ex);
                }
            }
        }
    }


}