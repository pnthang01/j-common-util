package com.etybeno.google;

import com.etybeno.common.service.ThreadPoolService;
import com.etybeno.google.model.BatchRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangpham on 23/12/2017.
 */
public class BatchRequestService implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchRequestService.class);
    private static BatchRequestService _instance;

    public static synchronized BatchRequestService _load() {
        if (null == _instance) _instance = new BatchRequestService();
        return _instance;
    }

    private ConcurrentMap<String, BatchRequestExecutor> batchMap;

    public BatchRequestService() {
        batchMap = new ConcurrentHashMap<>();
        ThreadPoolService.load().addThread(this, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public BatchRequestExecutor addBatchExecutor(BatchRequestExecutor batchExecutor) {
        BatchRequestExecutor executor = batchMap.putIfAbsent(batchExecutor.getName(), batchExecutor);
        return executor;
    }

    public void addRequest(String batchName, BatchRequestModel batchRequestModel) {
        BatchRequestExecutor batchRequestExecutor = batchMap.get(batchName);
        if (null == batchRequestExecutor)
            throw new NullPointerException("Batch executor with name: " + batchName + " does not exist");
        batchRequestExecutor.addRequest(batchRequestModel);
    }

    @Override
    public void run() {
        batchMap.forEach((s, batchRequestExecutor) -> {
            try {
                if(!batchRequestExecutor.isEmpty()) {
                    LOGGER.info("Start to execute batch: " + s);
                    batchRequestExecutor.execute();
                }
            } catch (Exception ex) {
                LOGGER.error("Error when executing batch: " + s);
            }
        });
    }
}
