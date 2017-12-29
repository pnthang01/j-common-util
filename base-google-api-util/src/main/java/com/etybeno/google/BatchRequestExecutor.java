package com.etybeno.google;

import com.google.api.client.googleapis.batch.BatchRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thangpham on 22/12/2017.
 */
public class BatchRequestExecutor {

    private BatchRequest batchRequest;
    private int limitBatch = 50;
    private int lingerMs = 1000;
    private String name;
    private long lastExecuteTime;
    private ConcurrentLinkedQueue<BatchRequestModel> requestQueue;

    public BatchRequestExecutor(String name, BatchRequest batchRequest) {
        this.batchRequest = batchRequest;
        this.name = name;
        requestQueue = new ConcurrentLinkedQueue<>();
        lastExecuteTime = System.currentTimeMillis();
    }

    public void addRequest(BatchRequestModel request) {
        requestQueue.add(request);
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public void execute() {
        if (System.currentTimeMillis() - lastExecuteTime >= lingerMs || requestQueue.size() >= limitBatch) {
            BatchRequestModel request = null;
            while ((request = requestQueue.poll()) != null) {
                try {
                    request.getRequest().queue(batchRequest, request.getCallback());
                    if(batchRequest.size() >= 500) batchRequest.execute(); //Google limit batch size
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getLingerMs() {
        return lingerMs;
    }

    public void setLingerMs(int lingerMs) {
        this.lingerMs = lingerMs;
    }

    public int getLimitBatch() {
        return limitBatch;
    }

    public void setLimitBatch(int limitBatch) {
        this.limitBatch = limitBatch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }
}
