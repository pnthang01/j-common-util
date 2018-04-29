package com.etybeno.google.drive.model;

import com.etybeno.google.BatchRequestExecutor;
import com.etybeno.google.BatchRequestService;
import com.etybeno.google.RetryBatchCallback;
import com.etybeno.google.loader.AbstractCredentialLoader;
import com.etybeno.google.model.BatchRequestModel;
import com.etybeno.google.util.SingletonUtil;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.services.drive.Drive;

/**
 * Created by thangpham on 24/04/2018.
 */
public class GoogleDriveConnection {

    private String appName;
    private DriveServiceInformation information;

    private Drive drive;
    private final BatchRequestService batchService = BatchRequestService._load();

    public GoogleDriveConnection(String appName, DriveServiceInformation information) throws Exception {
        this.appName = appName;
        this.information = information;
        //
        drive = new Drive.Builder(SingletonUtil.getHttpTransport(), SingletonUtil.getJsonFactory(), information.getCredential())
                .setApplicationName(appName)
                .build();
        batchService.addBatchExecutor(new BatchRequestExecutor(appName, drive.batch()));
    }

    public Drive drive() {
        return this.drive;
    }

    public void addBatch(AbstractGoogleJsonClientRequest request) {
        batchService.addRequest(appName, new BatchRequestModel(request, createCallback()));
    }

    public void addBatch(AbstractGoogleJsonClientRequest request, JsonBatchCallback callback) {
        batchService.addRequest(appName, new BatchRequestModel(request, callback));
    }

    private RetryBatchCallback createCallback() {
        return new RetryBatchCallback();
    }

    public String getAppName() {
        return appName;
    }

    public DriveServiceInformation getInformation() {
        return information;
    }

    public Drive getDrive() {
        return drive;
    }

}
