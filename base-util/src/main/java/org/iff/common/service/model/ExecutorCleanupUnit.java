package org.iff.common.service.model;

import java.util.concurrent.ExecutorService;

/**
 * Created by thangpham on 20/11/2017.
 */
public class ExecutorCleanupUnit {

    private String name;
    private ExecutorService executor;
    private long waitOnShutdown = 0;

    public ExecutorCleanupUnit(String name, ExecutorService executor, long waitOnShutdown) {
        this.name = name;
        this.executor = executor;
        this.waitOnShutdown = waitOnShutdown;
    }

    public ExecutorCleanupUnit(String name, ExecutorService executor) {
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public long getWaitOnShutdown() {
        return waitOnShutdown;
    }

    public void setWaitOnShutdown(long waitOnShutdown) {
        this.waitOnShutdown = waitOnShutdown;
    }
}