package com.etybeno.google;

import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;

import java.io.IOException;

/**
 * Created by thangpham on 22/12/2017.
 */
public class RetryBatchCallback<T> extends JsonBatchCallback<T> {

    @Override
    public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {

    }

    @Override
    public void onSuccess(T t, HttpHeaders responseHeaders) throws IOException {
        
    }
}
