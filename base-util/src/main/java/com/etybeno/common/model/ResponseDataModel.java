package com.etybeno.common.model;

import com.etybeno.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by thangpham on 13/12/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDataModel<T> {

    private Status status;
    private String message;
    private Pager pager;
    private T data;

    public Status getStatus() {
        return status;
    }

    public ResponseDataModel setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseDataModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDataModel setData(T data) {
        this.data = data;
        return this;
    }

    public Pager getPager() {
        return pager;
    }

    public ResponseDataModel setPager(Pager pager) {
        this.pager = pager;
        return this;
    }
}
