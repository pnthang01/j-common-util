package com.etybeno.google;

import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;

/**
 * Created by thangpham on 23/12/2017.
 */
public class BatchRequestModel<T> {
    private AbstractGoogleJsonClientRequest<T> request;
    private JsonBatchCallback callback;

    public BatchRequestModel() {
    }

    public BatchRequestModel(AbstractGoogleJsonClientRequest<T> request, JsonBatchCallback callback) {
        this.request = request;
        this.callback = callback;
    }

    public AbstractGoogleJsonClientRequest<T> getRequest() {
        return request;
    }

    public void setRequest(AbstractGoogleJsonClientRequest<T> request) {
        this.request = request;
    }

    public JsonBatchCallback getCallback() {
        return callback;
    }

    public void setCallback(JsonBatchCallback callback) {
        this.callback = callback;
    }
}
